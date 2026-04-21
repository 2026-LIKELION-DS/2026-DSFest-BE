package com.ds.dsfest.domain.schedule.dto;

import java.time.LocalDate;
import java.util.List;

public record FestivalScheduleDayResDto(
    int festivalDay, LocalDate date, String dayLabel, List<ScheduleItemResDto> schedules) {}
