package com.ds.dsfest.domain.artist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ds.dsfest.domain.artist.dto.ArtistListResponseDto;
import com.ds.dsfest.domain.artist.dto.ArtistPlaylistResponseDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Artist", description = "아티스트 관련 API")
public interface ArtistControllerDocs {

  @Operation(summary = "DAY별 아티스트 목록 조회", description = "특정 DAY에 해당하는 아티스트 목록을 조회합니다.")
  ResponseEntity<ApiResponse<List<ArtistListResponseDto>>> getArtistsByDay(int day);

  @Operation(summary = "덕우들의 플레이리스트 조회", description = "아티스트의 유튜브 플레이리스트 링크를 조회합니다.")
  ResponseEntity<ApiResponse<ArtistPlaylistResponseDto>> getArtistPlaylist(Long artistId);
}
