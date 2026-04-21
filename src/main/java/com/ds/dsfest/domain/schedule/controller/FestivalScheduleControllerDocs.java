package com.ds.dsfest.domain.schedule.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ds.dsfest.domain.schedule.dto.FestivalScheduleDayResDto;
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
}
