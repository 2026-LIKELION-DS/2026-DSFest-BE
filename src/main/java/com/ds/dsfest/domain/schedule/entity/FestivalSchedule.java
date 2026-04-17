package com.ds.dsfest.domain.schedule.entity;

import java.time.LocalDateTime;

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

import com.ds.dsfest.domain.artist.entity.Artist;
import com.ds.dsfest.domain.booth.entity.Booth;
import com.ds.dsfest.domain.schedule.constant.ScheduleType;
import com.ds.dsfest.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "festival_schedules")
public class FestivalSchedule extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String title;

  @Column(length = 500)
  private String description;

  @Column(nullable = false)
  private int festivalDay;

  @Column(nullable = false)
  private LocalDateTime startTime;

  private LocalDateTime endTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ScheduleType scheduleType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "artist_id")
  private Artist artist;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "booth_id")
  private Booth booth;
}
