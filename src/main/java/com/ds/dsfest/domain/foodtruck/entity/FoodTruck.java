package com.ds.dsfest.domain.foodtruck.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.ds.dsfest.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "food_trucks")
public class FoodTruck extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 100)
  private String representativeMenu;

  @Column(columnDefinition = "TEXT")
  private String description;

  @OrderBy("id ASC")
  @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FoodTruckImage> foodTruckImages = new ArrayList<>();

  @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FoodTruckMenu> menus = new ArrayList<>();

  @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FoodTruckOperatingDay> operatingDays = new ArrayList<>();
}
