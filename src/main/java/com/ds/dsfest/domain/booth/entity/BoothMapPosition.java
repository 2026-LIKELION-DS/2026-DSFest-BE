package com.ds.dsfest.domain.booth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "booth_map_positions")
public class BoothMapPosition extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private int festivalDay;

  @Enumerated(EnumType.STRING)
  @Column(name = "day_night_type", nullable = false, length = 10)
  private BoothType dayNightType;

  @Column(nullable = false)
  private int positionNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "booth_id", nullable = false)
  private Booth booth;
}
