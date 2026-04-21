package com.ds.dsfest.domain.booth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ds.dsfest.domain.booth.entity.Booth;

public interface BoothRepository extends JpaRepository<Booth, Long> {

  @Query(
      "SELECT DISTINCT b FROM Booth b "
          + "LEFT JOIN FETCH b.operatingDays "
          + "LEFT JOIN FETCH b.boothTypes "
          + "WHERE b.id = :id")
  Optional<Booth> findDetailById(@Param("id") Long id);
}
