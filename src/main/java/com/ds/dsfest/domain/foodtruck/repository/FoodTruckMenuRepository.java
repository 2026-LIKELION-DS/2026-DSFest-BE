package com.ds.dsfest.domain.foodtruck.repository;

import com.ds.dsfest.domain.foodtruck.entity.FoodTruck;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruckMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodTruckMenuRepository extends JpaRepository<FoodTruckMenu, Long> {
    /**
     * 특정 푸드트럭에 속한 모든 메뉴를 조회합니다.
     */
    List<FoodTruckMenu> findByFoodTruck(FoodTruck foodTruck);
}
