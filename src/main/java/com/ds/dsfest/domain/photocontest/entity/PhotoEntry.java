package com.ds.dsfest.domain.photocontest.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.ds.dsfest.domain.photocontest.constant.PhotoTheme;
import com.ds.dsfest.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "photo_entries")
public class PhotoEntry extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false, length = 50)
  private String authorName;

  @Column(nullable = false, length = 500)
  private String imageUrl;

  @OneToMany(mappedBy = "photoEntry", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PhotoVote> votes = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private PhotoTheme theme;
}
