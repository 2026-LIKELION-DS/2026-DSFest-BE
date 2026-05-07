package com.ds.dsfest.domain.booth.controller;

import java.util.List;

import jakarta.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.dto.BoothDetailResDto;
import com.ds.dsfest.domain.booth.dto.BoothListItemResDto;
import com.ds.dsfest.domain.booth.dto.BoothMapItemResDto;
import com.ds.dsfest.domain.booth.dto.BoothStatusItemResDto;
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

  @Operation(
      summary = "부스 상세 조회",
      description =
          "부스 ID로 상세 정보를 조회합니다. 운영일시·운영진(operatingSubject)·오픈카카오·에브리타임·인스타 링크,"
              + " 태그, 이미지, 각 운영일자의 위치 번호를 함께 반환합니다."
              + " tags 첫 번째 항목에는 현재 시각 기준 상태(운영중/운영 예정/운영 종료)가 들어갑니다.")
  ResponseEntity<ApiResponse<BoothDetailResDto>> getBoothDetail(@PathVariable Long boothId);

  @Operation(
      summary = "부스 리스트 (운영중 필터)",
      description =
          "festivalDay 기준 부스 목록을 각 부스의 상태(운영중/운영 예정/운영 종료)와 함께 반환합니다."
              + " active=true 적용 시: (1) 현재 운영 중인 부스가 있으면 '운영중' 부스만,"
              + " (2) 없고 곧 시작될 슬롯이 남아있으면 '운영 예정' 부스만(낮 종료~밤 시작 전엔 밤 부스),"
              + " (3) 모든 슬롯 종료 후엔 빈 리스트를 반환합니다."
              + " active=false(기본) 시 모든 부스를 상태 태그와 함께 반환합니다.")
  ResponseEntity<ApiResponse<List<BoothStatusItemResDto>>> getBoothsWithStatus(
      @RequestParam @Min(1) int day,
      @RequestParam(required = false, defaultValue = "false") boolean active);

  @Operation(
      summary = "랜덤 부스 추천",
      description =
          "festivalDay 기준 '운영중' 또는 '운영 예정' 상태인 부스 중 무작위 1개를 반환합니다."
              + " 응답에는 해당 festivalDay 기준 부스 위치 번호(positionNumber)도 포함됩니다."
              + " 같은 날 낮·밤 자리가 모두 있는 경우 현재 운영중인 슬롯 → 가장 가까운 운영예정 슬롯의 자리를 사용합니다."
              + " 위치 정보가 없으면 positionNumber는 null이며,"
              + " 해당 상태의 부스가 하나도 없으면(모두 '운영 종료') result가 null입니다.")
  ResponseEntity<ApiResponse<BoothStatusItemResDto>> getRandomRecommendedBooth(
      @RequestParam @Min(1) int day);
}
