package com.ds.dsfest.domain.foodtruck.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.ds.dsfest.domain.user.entity.GuestUser;
import com.ds.dsfest.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    name = "food_truck_likes",
    uniqueConstraints =
        @UniqueConstraint(
            name = "uk_food_truck_likes_guest_truck",
            columnNames = {"guest_uuid", "food_truck_id"}))
public class FoodTruckLike extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "guest_uuid", nullable = false)
  private GuestUser guestUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_truck_id", nullable = false)
  private FoodTruck foodTruck;
}
