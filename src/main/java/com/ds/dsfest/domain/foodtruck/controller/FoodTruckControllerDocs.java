package com.ds.dsfest.domain.foodtruck.controller;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckDetailResDto;
import com.ds.dsfest.domain.foodtruck.dto.FoodTruckLikeResDto;
import com.ds.dsfest.domain.foodtruck.dto.FoodTruckListResDto;
import com.ds.dsfest.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

/**
 * 푸드트럭 관련 API Swagger 문서화 인터페이스
 */
@Tag(name = "FoodTruck", description = "푸드트럭 관련 API")
public interface FoodTruckControllerDocs {

    /**
     * 푸드트럭 목록 조회 API
     */
    @Operation(summary = "푸드트럭 리스트 조회", description = "푸드트럭 메인 탭에 표시될 트럭 목록을 조회합니다. 비건 필터를 적용할 수 있습니다.")
    @Parameter(name = "is-vegan", description = "비건 메뉴 포함 트럭만 필터링 여부 (기본값: false)", required = false, in = ParameterIn.QUERY)
    ResponseEntity<ApiResponse<List<FoodTruckListResDto>>> getFoodTruckList(
        @RequestParam(value = "is-vegan", required = false, defaultValue = "false") boolean isVegan
    );

    /**
     * 푸드트럭 맛있어요 토글 API
     */
    @Operation(summary = "푸드트럭 맛있어요 토글", description = "좋아요가 없으면 생성(isLiked: true), 이미 존재하면 삭제(isLiked: false)됩니다.")
    @Parameter(name = "guest_uuid", description = "비회원 식별용 UUID (커스텀 헤더)", required = true, in = ParameterIn.HEADER)
    ResponseEntity<ApiResponse<FoodTruckLikeResDto>> toggleFoodTruckLike(
        @PathVariable Long foodTruckId,
        @RequestHeader("guest_uuid") UUID guestUuid
    );

    /**
     * 푸드트럭 상세 조회 API
     */
    @Operation(summary = "푸드트럭 상세 조회", description = "특정 푸드트럭의 메뉴, 이미지 목록, 운영 시간 및 좋아요 여부 등 상세 정보를 조회합니다.")
    @Parameter(name = "guest_uuid", description = "비회원 식별용 UUID (현재 사용자의 좋아요 여부 판별용. 필수는 아님)", required = false, in = ParameterIn.HEADER)
    ResponseEntity<ApiResponse<FoodTruckDetailResDto>> getFoodTruckDetail(
        @Parameter(description = "조회할 푸드트럭의 고유 ID", required = true) @PathVariable Long foodTruckId,
        @RequestHeader(value = "guest_uuid", required = false) UUID guestUuid
    );
}
