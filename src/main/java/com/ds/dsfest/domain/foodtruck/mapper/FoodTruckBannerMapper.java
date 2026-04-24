package com.ds.dsfest.domain.foodtruck.mapper;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckBannerResDto;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruckBanner;
import org.springframework.stereotype.Component;

@Component
public class FoodTruckBannerMapper {

    /**
     * FoodTruckBanner 엔티티를 응답 DTO로 변환
     */
    public FoodTruckBannerResDto toResDto(FoodTruckBanner banner) {
        return new FoodTruckBannerResDto(
            banner.getId(),
            banner.getImageUrl(),
            banner.getTitle()
        );
    }
}
