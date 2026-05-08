package com.ds.dsfest.domain.artist.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ds.dsfest.domain.artist.entity.CountdownStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArtistListResDto {

  private Long id;
  private String name;
  private String shortBio;

  private int festivalDay;
  private LocalDate performanceDate;
  private LocalTime startTime;
  private LocalTime endTime;

  private String imageUrl;
  private String instagramUrl;
  private String youtubeUrl;

  private String playlistUrl;
  private CountdownStatus countdownStatus;
}
