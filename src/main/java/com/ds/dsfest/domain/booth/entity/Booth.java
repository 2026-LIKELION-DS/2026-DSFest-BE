package com.ds.dsfest.domain.booth.entity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "booths")
public class Booth extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private int boothNumber;

  @Column(nullable = false, length = 100)
  private String name;

  @ElementCollection(fetch = FetchType.LAZY, targetClass = BoothType.class)
  @CollectionTable(
      name = "booth_types",
      joinColumns = @JoinColumn(name = "booth_id", nullable = false))
  @Enumerated(EnumType.STRING)
  @Column(name = "booth_type", nullable = false, length = 10)
  private Set<BoothType> boothTypes = EnumSet.noneOf(BoothType.class);

  @Column(nullable = false, length = 100)
  private String operatingSubject;

  @Column(length = 100)
  private String affiliation;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(length = 500)
  private String openKakaoUrl;

  @Column(length = 500)
  private String everytimeUrl;

  @Column(length = 500)
  private String instagramUrl;

  @Column(length = 500)
  private String collabInstagramUrl;

  @Column(length = 500)
  private String youtubeUrl;

  @Column(length = 255)
  private String operatingDaysText;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(
      name = "booth_categories",
      joinColumns = @JoinColumn(name = "booth_id", nullable = false))
  @Column(name = "category_name", nullable = false, length = 50)
  private List<String> categories = new ArrayList<>();

  @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BoothTag> tags = new ArrayList<>();

  @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BoothImage> images = new ArrayList<>();

  @OneToMany(mappedBy = "booth", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BoothOperatingDay> operatingDays = new ArrayList<>();
}
