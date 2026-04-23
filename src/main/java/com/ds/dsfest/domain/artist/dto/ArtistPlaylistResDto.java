package com.ds.dsfest.domain.artist.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
// 덕우플레이리스트
public class ArtistPlaylistResDto {

  private Long artistId;
  private String artistName;
  private String youtubeUrl;
}
