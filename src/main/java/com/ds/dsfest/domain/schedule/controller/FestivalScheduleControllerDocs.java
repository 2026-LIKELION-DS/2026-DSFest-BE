package com.ds.dsfest.domain.schedule.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ds.dsfest.domain.schedule.dto.FestivalScheduleDayResDto;
import com.ds.dsfest.domain.schedule.dto.FestivalScheduleNowResDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "FestivalSchedule", description = "축제 일정 API")
public interface FestivalScheduleControllerDocs {

  @Operation(
      summary = "축제 일정 전체 조회",
      description =
          "festivalDay별로 그룹핑된 전체 축제 일정을 반환합니다. 각 그룹은 festivalDay 오름차순,"
              + " 그룹 내 일정은 startTime 오름차순으로 정렬됩니다.")
  ResponseEntity<ApiResponse<List<FestivalScheduleDayResDto>>> getAllSchedules();

  @Operation(
      summary = "실시간 축제 상태 조회",
      description =
          "현재 시각 기준으로 축제 진행 상태를 반환합니다. "
              + "IN_PROGRESS: current 배열에 현재 진행 중인 일정들. "
              + "UPCOMING: next에 다음에 시작될 일정 1건. "
              + "ENDED: 축제가 모두 종료되었음(또는 아직 일정이 등록되지 않음).")
  ResponseEntity<ApiResponse<FestivalScheduleNowResDto>> getCurrentStatus();
}
