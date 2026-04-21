package com.ds.dsfest.domain.booth.controller;

import java.util.List;

import jakarta.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.dto.BoothListItemResDto;
import com.ds.dsfest.domain.booth.dto.BoothMapItemResDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Booth", description = "부스 사용자 API")
public interface BoothControllerDocs {

  @Operation(
      summary = "DAY별 부스 목록 조회",
      description = "festivalDay에 해당하는 운영일이 있는 부스 목록을 boothNumber 오름차순으로 반환합니다.")
  ResponseEntity<ApiResponse<List<BoothListItemResDto>>> getBoothsByDay(
      @RequestParam @Min(1) int day);

  @Operation(
      summary = "낮/밤별 부스 목록 조회",
      description =
          "festivalDay + 낮/밤 타입으로 필터링한 부스 목록을 boothNumber 오름차순으로 반환합니다. "
              + "DAY는 startTime < 16:00, NIGHT는 startTime >= 16:00 슬롯을 조회합니다.")
  ResponseEntity<ApiResponse<List<BoothListItemResDto>>> getBoothsByDayAndType(
      @RequestParam @Min(1) int day, @RequestParam BoothType type);

  @Operation(
      summary = "부스 지도 조회",
      description =
          "festivalDay + 낮/밤 타입에 해당하는 부스 지도의 각 위치(positionNumber 1~35)에 배치된 부스 정보를 반환합니다.")
  ResponseEntity<ApiResponse<List<BoothMapItemResDto>>> getBoothMap(
      @RequestParam @Min(1) int day, @RequestParam BoothType type);
}
