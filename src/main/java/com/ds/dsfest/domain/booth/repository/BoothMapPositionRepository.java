package com.ds.dsfest.domain.booth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.entity.BoothMapPosition;

public interface BoothMapPositionRepository extends JpaRepository<BoothMapPosition, Long> {

  @Query(
      "SELECT DISTINCT p FROM BoothMapPosition p "
          + "JOIN FETCH p.booth b "
          + "LEFT JOIN FETCH b.tags "
          + "LEFT JOIN FETCH b.boothTypes "
          + "WHERE p.festivalDay = :festivalDay "
          + "  AND p.dayNightType = :dayNightType "
          + "ORDER BY p.positionNumber ASC")
  List<BoothMapPosition> findAllByDayAndType(
      @Param("festivalDay") int festivalDay, @Param("dayNightType") BoothType dayNightType);
}
