package com.ds.dsfest.domain.foodtruck.controller;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckListResDto;
import com.ds.dsfest.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 푸드트럭 관련 API Swagger 문서화 인터페이스
 */
@Tag(name = "FoodTruck", description = "푸드트럭 관련 API")
public interface FoodTruckControllerDocs {

    /**
     * 푸드트럭 목록 조회 API
     */
    @Operation(summary = "푸드트럭 리스트 조회", description = "푸드트럭 메인 탭에 표시될 트럭 목록을 조회합니다.")
    ResponseEntity<ApiResponse<List<FoodTruckListResDto>>> getFoodTruckList();
}
