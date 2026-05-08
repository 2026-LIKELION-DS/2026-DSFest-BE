package com.ds.dsfest.domain.foodtruck.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 푸드트럭 맛있어요(좋아요) 토글 상태 응답 DTO */
@Schema(description = "푸드트럭 좋아요 토글 응답 데이터")
public record FoodTruckLikeResDto(
    @Schema(description = "좋아요 최종 상태 (true: 추가됨, false: 취소됨)", example = "true") boolean isLiked) {}
