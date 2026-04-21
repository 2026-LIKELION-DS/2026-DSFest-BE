package com.ds.dsfest.domain.artist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ds.dsfest.domain.artist.dto.ArtistListResponseDto;
import com.ds.dsfest.domain.artist.dto.ArtistPlaylistResponseDto;
import com.ds.dsfest.domain.artist.service.ArtistService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artists")
public class ArtistController implements ArtistControllerDocs {

  private final ArtistService artistService;

  @Override
  @GetMapping
  public ResponseEntity<ApiResponse<List<ArtistListResponseDto>>> getArtistsByDay(
      @RequestParam int day) {
    return ResponseEntity.ok(ApiResponse.onSuccess(artistService.getArtistsByDay(day)));
  }

  @Override
  @GetMapping("/{artistId}/playlist")
  public ResponseEntity<ApiResponse<ArtistPlaylistResponseDto>> getArtistPlaylist(
      @PathVariable Long artistId) {
    return ResponseEntity.ok(ApiResponse.onSuccess(artistService.getArtistPlaylist(artistId)));
  }
}
