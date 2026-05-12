package com.ds.dsfest.domain.photocontest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ds.dsfest.domain.photocontest.dto.PhotoRankResDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin Photo Contest", description = "총학생회 전용 사진 콘테스트 투표 결과 관리 API")
@SecurityRequirement(name = "BearerAuth")
public interface AdminPhotoContestControllerDocs {

  @Operation(summary = "투표 결과 실시간 집계 조회", description = "총학생회 관리자가 사진 득표수 랭킹 전체를 조회합니다.")
  ResponseEntity<ApiResponse<List<PhotoRankResDto>>> getVoteResults(); // Map -> List
}
