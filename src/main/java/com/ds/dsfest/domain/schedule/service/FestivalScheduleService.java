package com.ds.dsfest.domain.schedule.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.schedule.dto.FestivalScheduleDayResDto;
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
