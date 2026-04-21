package com.ds.dsfest.domain.booth.controller;

import java.util.List;

import jakarta.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.ds.dsfest.domain.booth.dto.BoothListItemResDto;
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
}
