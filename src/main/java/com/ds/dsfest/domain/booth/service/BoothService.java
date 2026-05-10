package com.ds.dsfest.domain.booth.service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
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

  // 대학일자리본부 1·2가 day 3 DAY에 같은 자리(8번)를 공유 — 합성 아이템 하나로 합쳐서 노출.
  private static final Long MERGED_BOOTH_ID = 1011L;
  private static final Long MERGED_PEER_A_ID = 10L;
  private static final Long MERGED_PEER_B_ID = 11L;
  private static final int MERGED_FESTIVAL_DAY = 3;
  private static final BoothType MERGED_DAY_NIGHT = BoothType.DAY;
  private static final int MERGED_POSITION = 8;
  private static final String MERGED_NAME = "대학일자리본부 1, 2";
  private static final DateTimeFormatter MERGED_TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

  private final BoothRepository boothRepository;
  private final BoothOperatingDayRepository boothOperatingDayRepository;
  private final BoothMapPositionRepository boothMapPositionRepository;
  private final Clock clock;

  @Value("${festival.start-date}")
  private LocalDate festivalStartDate;

  private final Random random = new Random();

  public List<BoothListItemResDto> getBoothsByDay(int festivalDay) {
    return groupAndMap(boothOperatingDayRepository.findAllByFestivalDayWithBooth(festivalDay));
  }

  public List<BoothListItemResDto> getBoothsByDayAndType(int festivalDay, BoothType type) {
    LocalTime from = type == BoothType.DAY ? DAY_START : NIGHT_START;
    LocalTime to = type == BoothType.DAY ? DAY_END : NIGHT_END;
    return groupAndMap(
        boothOperatingDayRepository.findAllByFestivalDayAndTimeRange(festivalDay, from, to));
  }

  private List<BoothListItemResDto> groupAndMap(List<BoothOperatingDay> ods) {
    Map<Long, Booth> boothsById = new LinkedHashMap<>();
    Map<Long, List<BoothOperatingDay>> slotsByBooth = new LinkedHashMap<>();
    for (BoothOperatingDay od : ods) {
      Long bid = od.getBooth().getId();
      boothsById.putIfAbsent(bid, od.getBooth());
      List<BoothOperatingDay> slots = slotsByBooth.computeIfAbsent(bid, k -> new ArrayList<>());
      if (!slots.contains(od)) {
        slots.add(od);
      }
    }
    return boothsById.values().stream()
        .sorted(Comparator.comparingInt(Booth::getBoothNumber))
        .map(b -> BoothListItemResDto.from(b, slotsByBooth.get(b.getId())))
        .toList();
  }

  public List<BoothMapItemResDto> getBoothMap(int festivalDay, BoothType dayNightType) {
    List<BoothMapPosition> positions =
        boothMapPositionRepository.findAllByDayAndType(festivalDay, dayNightType);

    if (festivalDay == MERGED_FESTIVAL_DAY && dayNightType == MERGED_DAY_NIGHT) {
      return mergeUnivJobMapItems(positions);
    }

    return positions.stream().map(BoothMapItemResDto::from).toList();
  }

  private List<BoothMapItemResDto> mergeUnivJobMapItems(List<BoothMapPosition> positions) {
    List<BoothMapItemResDto> items = new ArrayList<>();
    boolean mergedAdded = false;
    for (BoothMapPosition p : positions) {
      Long bid = p.getBooth().getId();
      boolean isMergeTarget =
          (MERGED_PEER_A_ID.equals(bid) || MERGED_PEER_B_ID.equals(bid))
              && p.getPositionNumber() == MERGED_POSITION;
      if (isMergeTarget) {
        if (!mergedAdded) {
          items.add(buildMergedMapItem());
          mergedAdded = true;
        }
      } else {
        items.add(BoothMapItemResDto.from(p));
      }
    }
    return items;
  }

  private BoothMapItemResDto buildMergedMapItem() {
    return new BoothMapItemResDto(
        MERGED_BOOTH_ID,
        MERGED_PEER_A_ID.intValue(),
        MERGED_NAME,
        EnumSet.of(BoothType.DAY),
        MERGED_NAME,
        null,
        MERGED_POSITION);
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
    List<BoothStatusItemResDto> candidates = new ArrayList<>();
    for (BoothStatusItemResDto b : getBoothsWithStatus(festivalDay, false)) {
      if (b.tags().isEmpty()) continue;
      String status = b.tags().get(0);
      if (TAG_RUNNING.equals(status) || TAG_UPCOMING.equals(status)) {
        candidates.add(b);
      }
    }
    if (candidates.isEmpty()) return null;
    BoothStatusItemResDto picked = candidates.get(random.nextInt(candidates.size()));
    Integer positionNumber = resolveRecommendedPositionNumber(picked.id(), festivalDay);
    return picked.withPositionNumber(positionNumber);
  }

  private Integer resolveRecommendedPositionNumber(Long boothId, int festivalDay) {
    List<BoothMapPosition> positions =
        boothMapPositionRepository.findAllByBoothId(boothId).stream()
            .filter(p -> p.getFestivalDay() == festivalDay)
            .toList();
    if (positions.isEmpty()) return null;
    if (positions.size() == 1) return positions.get(0).getPositionNumber();

    BoothType preferredType = pickPreferredDayNightType(boothId, festivalDay);
    return positions.stream()
        .filter(p -> p.getDayNightType() == preferredType)
        .findFirst()
        .map(BoothMapPosition::getPositionNumber)
        .orElseGet(() -> positions.get(0).getPositionNumber());
  }

  private BoothType pickPreferredDayNightType(Long boothId, int festivalDay) {
    Booth booth = boothRepository.findDetailById(boothId).orElse(null);
    if (booth == null) return BoothType.DAY;

    LocalTime effective = resolveEffectiveTime(festivalDay, LocalDateTime.now(clock));
    BoothOperatingDay running = null;
    BoothOperatingDay earliestUpcoming = null;
    for (BoothOperatingDay od : booth.getOperatingDays()) {
      if (od.getFestivalDay() != festivalDay) continue;
      if (!effective.isBefore(od.getStartTime()) && effective.isBefore(od.getEndTime())) {
        running = od;
        break;
      } else if (effective.isBefore(od.getStartTime())) {
        if (earliestUpcoming == null
            || od.getStartTime().isBefore(earliestUpcoming.getStartTime())) {
          earliestUpcoming = od;
        }
      }
    }
    BoothOperatingDay relevant = running != null ? running : earliestUpcoming;
    if (relevant == null) return BoothType.DAY;
    return relevant.getStartTime().isBefore(NIGHT_START) ? BoothType.DAY : BoothType.NIGHT;
  }

  private LocalTime resolveEffectiveTime(int festivalDay, LocalDateTime now) {
    long diff = ChronoUnit.DAYS.between(festivalStartDate, now.toLocalDate());
    int todayFestivalDay = (int) diff + 1;
    if (festivalDay > todayFestivalDay) return LocalTime.MIN;
    if (festivalDay < todayFestivalDay) return LocalTime.MAX;
    return now.toLocalTime();
  }

  public BoothDetailResDto getBoothDetail(Long boothId) {
    if (MERGED_BOOTH_ID.equals(boothId)) {
      return buildMergedBoothDetail();
    }

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

  private BoothDetailResDto buildMergedBoothDetail() {
    Booth boothA =
        boothRepository
            .findDetailById(MERGED_PEER_A_ID)
            .orElseThrow(() -> new CustomException(BoothErrorCode.BOOTH_NOT_FOUND));
    Booth boothB =
        boothRepository
            .findDetailById(MERGED_PEER_B_ID)
            .orElseThrow(() -> new CustomException(BoothErrorCode.BOOTH_NOT_FOUND));

    boothA.getTags().size();
    boothA.getImages().size();
    boothB.getTags().size();
    boothB.getImages().size();

    String descA = boothA.getDescription() == null ? "" : boothA.getDescription();
    String descB = boothB.getDescription() == null ? "" : boothB.getDescription();
    String mergedDescription = (descA + "\n\n" + descB).trim();

    LocalDate mergedDate = festivalStartDate.plusDays((long) MERGED_FESTIVAL_DAY - 1);
    String header =
        mergedDate.getDayOfMonth() + "일(" + toKoreanDow(mergedDate.getDayOfWeek()) + ") ";
    List<String> operatingDays =
        boothA.getOperatingDays().stream()
            .filter(
                od ->
                    od.getFestivalDay() == MERGED_FESTIVAL_DAY
                        && od.getStartTime().isBefore(DAY_END))
            .sorted(Comparator.comparing(BoothOperatingDay::getStartTime))
            .map(
                od ->
                    header
                        + od.getStartTime().format(MERGED_TIME_FMT)
                        + "~"
                        + od.getEndTime().format(MERGED_TIME_FMT))
            .distinct()
            .toList();

    List<String> positions = List.of(MERGED_FESTIVAL_DAY + "일차 낮: " + MERGED_POSITION + "번");

    String statusTag = resolveMergedStatusTag(LocalDateTime.now(clock), mergedDate, boothA);
    List<String> tags = statusTag != null ? List.of(statusTag) : List.of();

    return new BoothDetailResDto(
        MERGED_BOOTH_ID,
        MERGED_PEER_A_ID.intValue(),
        MERGED_NAME,
        EnumSet.of(BoothType.DAY),
        MERGED_NAME,
        boothA.getAffiliation(),
        mergedDescription,
        null,
        null,
        null,
        null,
        null,
        tags,
        List.of(),
        List.of(),
        operatingDays,
        positions);
  }

  private String resolveMergedStatusTag(LocalDateTime now, LocalDate mergedDate, Booth boothA) {
    LocalTime nowTime = now.toLocalTime();
    LocalDate today = now.toLocalDate();

    if (today.isBefore(mergedDate)) return TAG_UPCOMING;
    if (today.isAfter(mergedDate)) return TAG_ENDED;

    boolean anyRunning = false;
    boolean anyUpcoming = false;
    for (BoothOperatingDay od : boothA.getOperatingDays()) {
      if (od.getFestivalDay() != MERGED_FESTIVAL_DAY) continue;
      if (!od.getStartTime().isBefore(DAY_END)) continue; // DAY 슬롯만
      if (!nowTime.isBefore(od.getStartTime()) && nowTime.isBefore(od.getEndTime())) {
        anyRunning = true;
      } else if (nowTime.isBefore(od.getStartTime())) {
        anyUpcoming = true;
      }
    }
    if (anyRunning) return TAG_RUNNING;
    if (anyUpcoming) return TAG_UPCOMING;
    return TAG_ENDED;
  }

  private static String toKoreanDow(DayOfWeek dow) {
    return switch (dow) {
      case MONDAY -> "월";
      case TUESDAY -> "화";
      case WEDNESDAY -> "수";
      case THURSDAY -> "목";
      case FRIDAY -> "금";
      case SATURDAY -> "토";
      case SUNDAY -> "일";
    };
  }
}
