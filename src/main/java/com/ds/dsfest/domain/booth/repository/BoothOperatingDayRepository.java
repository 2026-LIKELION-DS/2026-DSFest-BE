package com.ds.dsfest.domain.booth.repository;

import com.ds.dsfest.domain.booth.entity.BoothOperatingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoothOperatingDayRepository extends JpaRepository<BoothOperatingDay, Long> {

  @Query(
      "SELECT DISTINCT od FROM BoothOperatingDay od "
          + "JOIN FETCH od.booth b "
          + "LEFT JOIN FETCH b.tags "
          + "LEFT JOIN FETCH b.boothTypes "
          + "LEFT JOIN FETCH b.images "
          + "WHERE od.festivalDay = :festivalDay "
          + "ORDER BY b.boothNumber ASC")
  List<BoothOperatingDay> findAllByFestivalDayWithBooth(@Param("festivalDay") int festivalDay);
}
