package com.ds.dsfest.domain.foodtruck.entity;

import jakarta.persistence.*;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "food_truck_images")
public class FoodTruckImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_truck_id", nullable = false)
  private FoodTruck foodTruck;

  @Column(nullable = false, length = 500)
  private String imageUrl;
}
