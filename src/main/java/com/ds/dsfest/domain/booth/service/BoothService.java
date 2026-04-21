package com.ds.dsfest.domain.booth.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.dto.BoothDetailResDto;
import com.ds.dsfest.domain.booth.dto.BoothListItemResDto;
import com.ds.dsfest.domain.booth.dto.BoothMapItemResDto;
import com.ds.dsfest.domain.booth.dto.BoothStatusItemResDto;
import com.ds.dsfest.domain.booth.entity.Booth;
import com.ds.dsfest.domain.booth.entity.BoothMapPosition;
import com.ds.dsfest.domain.booth.entity.BoothOperatingDay;
import com.ds.dsfest.domain.booth.exception.BoothErrorCode;
import com.ds.dsfest.domain.booth.repository.BoothMapPositionRepository;
import com.ds.dsfest.domain.booth.repository.BoothOperatingDayRepository;
import com.ds.dsfest.domain.booth.repository.BoothRepository;
import com.ds.dsfest.global.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoothService {

  private static final LocalTime DAY_START = LocalTime.of(0, 0);
  private static final LocalTime DAY_END = LocalTime.of(16, 0);
  private static final LocalTime NIGHT_START = LocalTime.of(16, 0);
  private static final LocalTime NIGHT_END = LocalTime.of(23, 59, 59);

  private static final String TAG_RUNNING = "운영중";
  private static final String TAG_UPCOMING = "운영 예정";
  private static final String TAG_ENDED = "운영 종료";

  private final BoothRepository boothRepository;
  private final BoothOperatingDayRepository boothOperatingDayRepository;
  private final BoothMapPositionRepository boothMapPositionRepository;

  @Value("${festival.start-date}")
  private LocalDate festivalStartDate;

  private final Clock clock = Clock.systemDefaultZone();
  private final Random random = new Random();

  public List<BoothListItemResDto> getBoothsByDay(int festivalDay) {
    return boothOperatingDayRepository.findAllByFestivalDayWithBooth(festivalDay).stream()
        .map(operatingDay -> BoothListItemResDto.from(operatingDay.getBooth(), operatingDay))
        .toList();
  }

  public List<BoothListItemResDto> getBoothsByDayAndType(int festivalDay, BoothType type) {
    LocalTime from = type == BoothType.DAY ? DAY_START : NIGHT_START;
    LocalTime to = type == BoothType.DAY ? DAY_END : NIGHT_END;
    return boothOperatingDayRepository
        .findAllByFestivalDayAndTimeRange(festivalDay, from, to)
        .stream()
        .map(operatingDay -> BoothListItemResDto.from(operatingDay.getBooth(), operatingDay))
        .toList();
  }

  public List<BoothMapItemResDto> getBoothMap(int festivalDay, BoothType dayNightType) {
    return boothMapPositionRepository.findAllByDayAndType(festivalDay, dayNightType).stream()
        .map(BoothMapItemResDto::from)
        .toList();
  }

  public List<BoothStatusItemResDto> getBoothsWithStatus(int festivalDay, boolean activeOnly) {
    List<BoothOperatingDay> ods =
        boothOperatingDayRepository.findAllByFestivalDayWithBooth(festivalDay);

    Map<Long, Booth> boothsById = new LinkedHashMap<>();
    Map<Long, List<BoothOperatingDay>> slotsByBooth = new LinkedHashMap<>();
    for (BoothOperatingDay od : ods) {
      Long bid = od.getBooth().getId();
      boothsById.putIfAbsent(bid, od.getBooth());
      slotsByBooth.computeIfAbsent(bid, k -> new ArrayList<>()).add(od);
    }

    LocalDateTime now = LocalDateTime.now(clock);
    LocalTime effectiveTime = resolveEffectiveTime(festivalDay, now);

    Map<Long, String> statusByBooth = new HashMap<>();
    Map<Long, LocalTime> earliestUpcomingByBooth = new HashMap<>();
    boolean anyRunning = false;
    boolean anyUpcoming = false;
    for (Map.Entry<Long, List<BoothOperatingDay>> e : slotsByBooth.entrySet()) {
      boolean running = false;
      LocalTime earliestUpcoming = null;
      for (BoothOperatingDay od : e.getValue()) {
        if (!effectiveTime.isBefore(od.getStartTime()) && effectiveTime.isBefore(od.getEndTime())) {
          running = true;
        } else if (effectiveTime.isBefore(od.getStartTime())) {
          if (earliestUpcoming == null || od.getStartTime().isBefore(earliestUpcoming)) {
            earliestUpcoming = od.getStartTime();
          }
        }
      }
      boolean upcoming = earliestUpcoming != null;
      String s = running ? TAG_RUNNING : (upcoming ? TAG_UPCOMING : TAG_ENDED);
      statusByBooth.put(e.getKey(), s);
      if (upcoming) earliestUpcomingByBooth.put(e.getKey(), earliestUpcoming);
      if (running) anyRunning = true;
      if (upcoming) anyUpcoming = true;
    }

    Comparator<Booth> statusComparator =
        Comparator.<Booth, Integer>comparing(b -> statusPriority(statusByBooth.get(b.getId())))
            .thenComparing(
                b -> earliestUpcomingByBooth.getOrDefault(b.getId(), LocalTime.MIN),
                Comparator.naturalOrder())
            .thenComparingInt(Booth::getBoothNumber);

    if (activeOnly) {
      String filterStatus = anyRunning ? TAG_RUNNING : (anyUpcoming ? TAG_UPCOMING : null);
      if (filterStatus == null) {
        return List.of();
      }
      return boothsById.values().stream()
          .filter(b -> filterStatus.equals(statusByBooth.get(b.getId())))
          .sorted(statusComparator)
          .map(b -> BoothStatusItemResDto.from(b, statusByBooth.get(b.getId())))
          .toList();
    }

    return boothsById.values().stream()
        .sorted(statusComparator)
        .map(b -> BoothStatusItemResDto.from(b, statusByBooth.get(b.getId())))
        .toList();
  }

  private int statusPriority(String status) {
    if (TAG_RUNNING.equals(status)) return 0;
    if (TAG_UPCOMING.equals(status)) return 1;
    return 2;
  }

  public BoothStatusItemResDto getRandomRecommendedBooth(int festivalDay) {
    List<BoothStatusItemResDto> running = new ArrayList<>();
    for (BoothStatusItemResDto b : getBoothsWithStatus(festivalDay, false)) {
      if (!b.tags().isEmpty() && TAG_RUNNING.equals(b.tags().get(0))) {
        running.add(b);
      }
    }
    if (running.isEmpty()) return null;
    return running.get(random.nextInt(running.size()));
  }

  private LocalTime resolveEffectiveTime(int festivalDay, LocalDateTime now) {
    long diff = ChronoUnit.DAYS.between(festivalStartDate, now.toLocalDate());
    int todayFestivalDay = (int) diff + 1;
    if (festivalDay > todayFestivalDay) return LocalTime.MIN;
    if (festivalDay < todayFestivalDay) return LocalTime.MAX;
    return now.toLocalTime();
  }

  public BoothDetailResDto getBoothDetail(Long boothId) {
    Booth booth =
        boothRepository
            .findDetailById(boothId)
            .orElseThrow(() -> new CustomException(BoothErrorCode.BOOTH_NOT_FOUND));

    // lazy 컬렉션 강제 초기화
    booth.getTags().size();
    booth.getImages().size();

    List<BoothMapPosition> positions = boothMapPositionRepository.findAllByBoothId(boothId);

    String statusTag = resolveStatusTag(booth, LocalDateTime.now(clock));

    return BoothDetailResDto.from(booth, positions, statusTag, festivalStartDate);
  }

  private String resolveStatusTag(Booth booth, LocalDateTime now) {
    LocalDate today = now.toLocalDate();
    LocalTime nowTime = now.toLocalTime();
    long diffDays = ChronoUnit.DAYS.between(festivalStartDate, today);
    int todayFestivalDay = (int) diffDays + 1;

    boolean anyRunning = false;
    boolean anyUpcoming = false;

    for (BoothOperatingDay od : booth.getOperatingDays()) {
      int day = od.getFestivalDay();
      if (day > todayFestivalDay) {
        anyUpcoming = true;
      } else if (day == todayFestivalDay) {
        if (!nowTime.isBefore(od.getStartTime()) && nowTime.isBefore(od.getEndTime())) {
          anyRunning = true;
        } else if (nowTime.isBefore(od.getStartTime())) {
          anyUpcoming = true;
        }
      }
    }

    if (anyRunning) return TAG_RUNNING;
    if (anyUpcoming) return TAG_UPCOMING;
    return TAG_ENDED;
  }
}
