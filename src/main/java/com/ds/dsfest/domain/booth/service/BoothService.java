package com.ds.dsfest.domain.booth.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.dto.BoothDetailResDto;
import com.ds.dsfest.domain.booth.dto.BoothListItemResDto;
import com.ds.dsfest.domain.booth.dto.BoothMapItemResDto;
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
