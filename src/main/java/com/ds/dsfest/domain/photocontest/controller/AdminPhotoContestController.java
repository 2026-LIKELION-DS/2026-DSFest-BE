package com.ds.dsfest.domain.photocontest.controller;

import com.ds.dsfest.domain.photocontest.dto.PhotoRankResDto;
import com.ds.dsfest.domain.photocontest.service.PhotoContestService;
import com.ds.dsfest.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/photo-contest")
public class AdminPhotoContestController implements AdminPhotoContestControllerDocs { // 💡 Docs 구현!

  private final PhotoContestService photoContestService;

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/results")
  public ResponseEntity<ApiResponse<List<PhotoRankResDto>>> getVoteResults() {
      List<PhotoRankResDto> results = photoContestService.getVoteResults(true);
      return ResponseEntity.ok(ApiResponse.onSuccess(results));
  }
}
