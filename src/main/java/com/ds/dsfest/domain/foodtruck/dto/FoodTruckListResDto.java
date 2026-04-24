package com.ds.dsfest.domain.foodtruck.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 푸드트럭 리스트 조회를 위한 응답 DTO
 */
@Schema(description = "푸드트럭 리스트 응답 데이터")
public record FoodTruckListResDto(

    @Schema(description = "푸드트럭 고유 식별자", example = "1")
    Long id,

    @Schema(description = "푸드트럭 썸네일 이미지 URL", example = "https://dsfest.s3.ap-northeast-2.amazonaws.com/food-trucks/creamy.jpg")
    String imageUrl,

    @Schema(description = "푸드트럭 이름", example = "크래미 분식집")
    String name,

    @Schema(description = "대표 메뉴 이름 (태그 하단 표시용)", example = "크래미 김밥")
    String representativeMenu,

    @Schema(description = "푸드트럭 태그 (해시태그 형태)", example = "#간편식 #고기/BBQ")
    String description,

    @Schema(description = "운영 날짜 및 시간 요약 (오늘 날짜 기준)", example = "13일(수) 13:00 - 20:00")
    String operatingDays,

    @Schema(description = "맛있어요(좋아요) 누적 개수", example = "999")
    Integer likeCount,

    @Schema(description = "현재 운영 중 여부 (true: 운영 중, false: 영업 종료/휴무)", example = "true")
    Boolean isOpen
) {
}
