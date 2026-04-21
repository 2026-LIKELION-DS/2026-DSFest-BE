package com.ds.dsfest.domain.schedule.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds.dsfest.domain.schedule.dto.FestivalScheduleDayResDto;
import com.ds.dsfest.domain.schedule.dto.FestivalScheduleNowResDto;
import com.ds.dsfest.domain.schedule.service.FestivalScheduleService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class FestivalScheduleController implements FestivalScheduleControllerDocs {

  private final FestivalScheduleService festivalScheduleService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<FestivalScheduleDayResDto>>> getAllSchedules() {
    return ResponseEntity.ok(ApiResponse.onSuccess(festivalScheduleService.getAllSchedules()));
  }

  @GetMapping("/now")
  public ResponseEntity<ApiResponse<FestivalScheduleNowResDto>> getCurrentStatus() {
    return ResponseEntity.ok(ApiResponse.onSuccess(festivalScheduleService.getCurrentStatus()));
  }
}
