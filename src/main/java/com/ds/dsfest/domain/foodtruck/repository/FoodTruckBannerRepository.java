package com.ds.dsfest.domain.foodtruck.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.foodtruck.entity.FoodTruckBanner;

public interface FoodTruckBannerRepository extends JpaRepository<FoodTruckBanner, Long> {

  /** 배너를 순서(bannerOrder) 오름차순으로 모두 가져오는 쿼리 메서드 */
  List<FoodTruckBanner> findAllByOrderByBannerOrderAsc();
}
