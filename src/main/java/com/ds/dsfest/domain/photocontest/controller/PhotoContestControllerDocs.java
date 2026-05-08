package com.ds.dsfest.domain.photocontest.controller;

import com.ds.dsfest.domain.photocontest.dto.*;
import com.ds.dsfest.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Tag(name = "Photo Contest", description = "사진 콘테스트 관련 API")
public interface PhotoContestControllerDocs {

  @Operation(
      summary = "사진 콘테스트 상태 조회",
      description = "현재 콘테스트 상태(ACCEPTING, VOTING, ENDED)를 반환합니다.")
  ResponseEntity<ApiResponse<PhotoContestStatusResDto>> getContestStatus();

  @Operation(summary = "사진 상세 조회", description = "특정 사진의 상세 정보를 조회합니다.")
  ResponseEntity<ApiResponse<PhotoDetailResDto>> getPhotoDetail(
      @PathVariable(name = "photoEntryId") Long photoEntryId);

  @Operation(summary = "사진 목록 조회", description = "주제별(청춘, 축제, 드레스코드)로 분류된 사진 목록을 조회합니다.")
  ResponseEntity<ApiResponse<PhotoListResDto>> getPhotoList();

  @Operation(summary = "사진 투표하기", description = "선택한 3장의 사진에 투표합니다. (1인 1회 제한, 주제별 1장씩 필수)")
  ResponseEntity<ApiResponse<String>> votePhotos(@Valid @RequestBody PhotoVoteReqDto reqDto);

    @Operation(summary = "실시간 사진 랭킹 조회", description = "테마별 사진 득표수 랭킹을 조회합니다. (15일 15시 이후 비공개)")
    ResponseEntity<ApiResponse<Map<String, List<PhotoRankResDto>>>> getStudentRank();
}
