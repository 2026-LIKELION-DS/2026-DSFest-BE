package com.ds.dsfest.domain.foodtruck.repository;

import com.ds.dsfest.domain.foodtruck.entity.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 푸드트럭 엔티티에 접근하기 위한 레포지토리 인터페이스
 */
@Repository
public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {

    /**
     * 푸드트럭과 운영 일정(operatingDays)을 한 번의 쿼리로 함께 조회합니다.
     */
    @Query("SELECT DISTINCT f FROM FoodTruck f JOIN FETCH f.operatingDays")
    List<FoodTruck> findAllWithOperatingDays();
}
