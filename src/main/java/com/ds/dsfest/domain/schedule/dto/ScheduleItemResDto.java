package com.ds.dsfest.domain.schedule.dto;

import java.time.LocalDateTime;

import com.ds.dsfest.domain.schedule.constant.ScheduleType;
import com.ds.dsfest.domain.schedule.entity.FestivalSchedule;

public record ScheduleItemResDto(
    Long id,
    String title,
    String description,
    LocalDateTime startTime,
    LocalDateTime endTime,
    ScheduleType scheduleType) {

  public static ScheduleItemResDto from(FestivalSchedule s) {
    return new ScheduleItemResDto(
        s.getId(),
        s.getTitle(),
        s.getDescription(),
        s.getStartTime(),
        s.getEndTime(),
        s.getScheduleType());
  }
}
