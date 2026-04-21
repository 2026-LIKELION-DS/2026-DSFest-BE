package com.ds.dsfest.domain.schedule.dto;

import java.util.List;

import com.ds.dsfest.domain.schedule.constant.FestivalStatus;

public record FestivalScheduleNowResDto(
    FestivalStatus status, List<ScheduleItemResDto> current, ScheduleItemResDto next) {

  public static FestivalScheduleNowResDto inProgress(List<ScheduleItemResDto> current) {
    return new FestivalScheduleNowResDto(FestivalStatus.IN_PROGRESS, current, null);
  }

  public static FestivalScheduleNowResDto upcoming(ScheduleItemResDto next) {
    return new FestivalScheduleNowResDto(FestivalStatus.UPCOMING, List.of(), next);
  }

  public static FestivalScheduleNowResDto ended() {
    return new FestivalScheduleNowResDto(FestivalStatus.ENDED, List.of(), null);
  }
}
