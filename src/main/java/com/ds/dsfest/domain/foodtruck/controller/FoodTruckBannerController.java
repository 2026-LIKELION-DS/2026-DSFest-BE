package com.ds.dsfest.domain.foodtruck.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckBannerResDto;
import com.ds.dsfest.domain.foodtruck.service.FoodTruckBannerService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

/** 푸드트럭 배너 API 컨트롤러 구현체 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food-trucks/banners")
public class FoodTruckBannerController implements FoodTruckBannerControllerDocs {

  private final FoodTruckBannerService foodTruckBannerService;

  /** 배너 목록 조회 API */
  @GetMapping
  @Override
  public ResponseEntity<ApiResponse<List<FoodTruckBannerResDto>>> getFoodTruckBanners() {
    List<FoodTruckBannerResDto> response = foodTruckBannerService.getFoodTruckBanners();

    return ResponseEntity.ok(ApiResponse.onSuccess(response));
  }
}
