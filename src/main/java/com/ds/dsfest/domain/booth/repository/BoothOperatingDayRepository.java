package com.ds.dsfest.domain.booth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ds.dsfest.domain.booth.entity.BoothOperatingDay;

public interface BoothOperatingDayRepository extends JpaRepository<BoothOperatingDay, Long> {

  @Query(
      "SELECT DISTINCT od FROM BoothOperatingDay od "
          + "JOIN FETCH od.booth b "
          + "LEFT JOIN FETCH b.tags "
          + "LEFT JOIN FETCH b.boothTypes "
          + "WHERE od.festivalDay = :festivalDay "
          + "ORDER BY b.boothNumber ASC")
  List<BoothOperatingDay> findAllByFestivalDayWithBooth(@Param("festivalDay") int festivalDay);
}
