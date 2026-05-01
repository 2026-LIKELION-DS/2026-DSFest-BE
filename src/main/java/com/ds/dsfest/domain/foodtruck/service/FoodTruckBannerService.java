package com.ds.dsfest.domain.foodtruck.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckBannerResDto;
import com.ds.dsfest.domain.foodtruck.mapper.FoodTruckBannerMapper;
import com.ds.dsfest.domain.foodtruck.repository.FoodTruckBannerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodTruckBannerService {

  private final FoodTruckBannerRepository foodTruckBannerRepository;
  private final FoodTruckBannerMapper foodTruckBannerMapper;

  /** 푸드트럭 배너 목록을 순서대로 조회하여 DTO로 반환합니다. */
  public List<FoodTruckBannerResDto> getFoodTruckBanners() {
    return foodTruckBannerRepository.findAllByOrderByBannerOrderAsc().stream()
        .map(foodTruckBannerMapper::toResDto)
        .collect(Collectors.toList());
  }
}
