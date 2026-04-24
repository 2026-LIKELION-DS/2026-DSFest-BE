package com.ds.dsfest.domain.foodtruck.controller;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckLikeResDto;
import com.ds.dsfest.domain.foodtruck.dto.FoodTruckListResDto;
import com.ds.dsfest.domain.foodtruck.service.FoodTruckService;
import com.ds.dsfest.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    /**
     * 특정 푸드트럭에 대한 '맛있어요(좋아요)' 토글
     */
    @PostMapping("/{foodTruckId}/likes")
    @Override
    public ResponseEntity<ApiResponse<FoodTruckLikeResDto>> toggleFoodTruckLike(
        @PathVariable Long foodTruckId,
        @RequestHeader("guest_uuid") UUID guestUuid
    ) {
        FoodTruckLikeResDto result = foodTruckService.toggleFoodTruckLike(foodTruckId, guestUuid);
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }
}
