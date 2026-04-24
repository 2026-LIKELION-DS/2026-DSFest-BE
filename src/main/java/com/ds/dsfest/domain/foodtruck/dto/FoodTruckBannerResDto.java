package com.ds.dsfest.domain.foodtruck.dto;

/**
 * 푸드트럭 탭 상단 배너 응답을 위한 DTO
 */
public record FoodTruckBannerResDto(
    Long id,
    String imageUrl,
    String title
) {
}
