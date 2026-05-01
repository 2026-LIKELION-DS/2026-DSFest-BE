package com.ds.dsfest.domain.foodtruck.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.foodtruck.entity.FoodTruck;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruckLike;
import com.ds.dsfest.domain.user.entity.GuestUser;

public interface FoodTruckLikeRepository extends JpaRepository<FoodTruckLike, Long> {
  /** 특정 유저와 트럭으로 좋아요 존재 여부 확인 */
  Optional<FoodTruckLike> findByGuestUserAndFoodTruck(GuestUser guestUser, FoodTruck foodTruck);

  long countByFoodTruck(FoodTruck foodTruck); // 특정 트럭의 총 좋아요 개수
}
