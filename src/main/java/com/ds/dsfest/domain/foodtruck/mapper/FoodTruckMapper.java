package com.ds.dsfest.domain.foodtruck.mapper;

import org.springframework.stereotype.Component;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckListResDto;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruck;

/** 푸드트럭 엔티티와 DTO 간의 변환을 담당하는 매퍼 */
@Component
public class FoodTruckMapper {

  /**
   * FoodTruck 엔티티를 FoodTruckListResDto로 변환합니다.
   *
   * @param foodTruck 푸드트럭 엔티티
   * @param operatingDays 운영 시간 문자열
   * @param likeCount 좋아요 개수
   * @return 푸드트럭 리스트 응답 DTO
   */
  public FoodTruckListResDto toFoodTruckListResDto(
      FoodTruck foodTruck,
      String thumbnailUrl,
      String operatingDays,
      Integer likeCount,
      Boolean isOpen) {
    return new FoodTruckListResDto(
        foodTruck.getId(),
        thumbnailUrl,
        foodTruck.getName(),
        foodTruck.getRepresentativeMenu(),
        foodTruck.getDescription(),
        operatingDays,
        likeCount,
        isOpen);
  }
}
