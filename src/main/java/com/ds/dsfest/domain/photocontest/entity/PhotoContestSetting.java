package com.ds.dsfest.domain.photocontest.entity;

import com.ds.dsfest.domain.photocontest.constant.PhotoContestStatus;
import com.ds.dsfest.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "photo_contest_settings")
public class PhotoContestSetting extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private PhotoContestStatus status;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  @Column(name = "end_time")
  private LocalDateTime endTime;
}
