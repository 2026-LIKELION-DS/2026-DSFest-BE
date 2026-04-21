package com.ds.dsfest.domain.schedule.service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.schedule.dto.FestivalScheduleDayResDto;
import com.ds.dsfest.domain.schedule.dto.FestivalScheduleNowResDto;
import com.ds.dsfest.domain.schedule.dto.ScheduleItemResDto;
import com.ds.dsfest.domain.schedule.entity.FestivalSchedule;
import com.ds.dsfest.domain.schedule.repository.FestivalScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FestivalScheduleService {

  private final FestivalScheduleRepository festivalScheduleRepository;

  @Value("${festival.start-date}")
  private LocalDate festivalStartDate;

  private final Clock clock = Clock.systemDefaultZone();

  public List<FestivalScheduleDayResDto> getAllSchedules() {
    List<FestivalSchedule> all =
        festivalScheduleRepository.findAllByOrderByFestivalDayAscStartTimeAsc();

    Map<Integer, List<ScheduleItemResDto>> byDay = new LinkedHashMap<>();
    for (FestivalSchedule s : all) {
      byDay
          .computeIfAbsent(s.getFestivalDay(), k -> new ArrayList<>())
          .add(ScheduleItemResDto.from(s));
    }

    List<FestivalScheduleDayResDto> result = new ArrayList<>();
    for (Map.Entry<Integer, List<ScheduleItemResDto>> e : byDay.entrySet()) {
      LocalDate date = festivalStartDate.plusDays(e.getKey() - 1L);
      String label =
          "DAY "
              + e.getKey()
              + " "
              + date.getMonthValue()
              + "/"
              + date.getDayOfMonth()
              + "("
              + toKoreanDow(date.getDayOfWeek())
              + ")";
      result.add(new FestivalScheduleDayResDto(e.getKey(), date, label, e.getValue()));
    }
    return result;
  }

  public FestivalScheduleNowResDto getCurrentStatus() {
    LocalDateTime now = LocalDateTime.now(clock);
    List<FestivalSchedule> all =
        festivalScheduleRepository.findAllByOrderByFestivalDayAscStartTimeAsc();

    if (all.isEmpty()) {
      return FestivalScheduleNowResDto.ended();
    }

    List<ScheduleItemResDto> active =
        all.stream()
            .filter(s -> !s.getStartTime().isAfter(now))
            .filter(s -> s.getEndTime() == null || s.getEndTime().isAfter(now))
            .map(ScheduleItemResDto::from)
            .toList();

    if (!active.isEmpty()) {
      return FestivalScheduleNowResDto.inProgress(active);
    }

    Optional<FestivalSchedule> next =
        all.stream().filter(s -> s.getStartTime().isAfter(now)).findFirst();

    return next.map(s -> FestivalScheduleNowResDto.upcoming(ScheduleItemResDto.from(s)))
        .orElseGet(FestivalScheduleNowResDto::ended);
  }

  private String toKoreanDow(DayOfWeek dow) {
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
