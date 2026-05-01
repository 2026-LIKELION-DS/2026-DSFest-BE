package com.ds.dsfest.domain.foodtruck.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckBannerResDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "FoodTruck", description = "푸드트럭 관련 API")
public interface FoodTruckBannerControllerDocs {

  @Operation(summary = "푸드트럭 배너 목록 조회", description = "푸드트럭 탭 상단에 표시될 배너 목록을 순서대로 조회합니다.")
  ResponseEntity<ApiResponse<List<FoodTruckBannerResDto>>> getFoodTruckBanners();
}
