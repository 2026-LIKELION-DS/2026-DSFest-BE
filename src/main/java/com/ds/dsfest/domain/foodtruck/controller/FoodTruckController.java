package com.ds.dsfest.domain.foodtruck.controller;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckListResDto;
import com.ds.dsfest.domain.foodtruck.service.FoodTruckService;
import com.ds.dsfest.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 푸드트럭 API 컨트롤러 구현체
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food-trucks")
public class FoodTruckController implements FoodTruckControllerDocs {

    private final FoodTruckService foodTruckService;

    /**
     * 푸드트럭 목록 조회 API
     */
    @GetMapping
    @Override
    public ResponseEntity<ApiResponse<List<FoodTruckListResDto>>> getFoodTruckList() {
        List<FoodTruckListResDto> result = foodTruckService.getFoodTruckList();

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }
}
