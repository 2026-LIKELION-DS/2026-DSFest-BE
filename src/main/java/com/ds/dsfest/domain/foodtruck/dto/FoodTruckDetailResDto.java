package com.ds.dsfest.domain.foodtruck.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "푸드트럭 상세 조회 응답 데이터")
public record FoodTruckDetailResDto(
    @Schema(description = "푸드트럭 고유 식별자", example = "1")
    Long id,

    @Schema(description = "푸드트럭 이미지 URL")
    List<String> imageUrls,

    @Schema(description = "푸드트럭 이름", example = "크래미 분식집")
    String name,

    @Schema(description = "푸드트럭 태그", example = "#간편식 #디저트")
    String description,

    @Schema(description = "오늘의 운영 시간 요약", example = "13일(수) 13:00 - 20:00")
    String operatingString,

    @Schema(description = "해당 푸드트럭의 메뉴 목록")
    List<FoodTruckMenuResDto> menus,

    @Schema(description = "맛있어요(좋아요) 누적 개수", example = "150")
    int likeCount,

    @Schema(description = "현재 접속한 사용자의 좋아요 클릭 여부", example = "true")
    boolean isLiked
) {
}
