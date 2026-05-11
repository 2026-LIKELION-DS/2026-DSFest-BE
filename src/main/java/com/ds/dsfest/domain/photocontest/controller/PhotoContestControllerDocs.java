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

@Tag(name = "Photo Contest", description = "사진 콘테스트 관련 API")
public interface PhotoContestControllerDocs {

  @Operation(
      summary = "사진 콘테스트 상태 조회",
      description = "현재 콘테스트 상태(ACCEPTING, VOTING, ENDED)를 반환합니다.")
  ResponseEntity<ApiResponse<PhotoContestStatusResDto>> getContestStatus();

  @Operation(summary = "사진 상세 조회", description = "특정 사진의 상세 정보를 조회합니다.")
  ResponseEntity<ApiResponse<PhotoDetailResDto>> getPhotoDetail(
      @PathVariable(name = "photoEntryId") Long photoEntryId);

    @Operation(summary = "사진 목록 조회", description = "출품된 사진 전체 목록을 조회합니다.")
    ResponseEntity<ApiResponse<PhotoListResDto>> getPhotoList();

    @Operation(summary = "사진 투표하기", description = "사진에 투표합니다. (1인당 투표 가능 횟수 제한 확인 필요)")
    ResponseEntity<ApiResponse<String>> votePhotos(@Valid @RequestBody PhotoVoteReqDto reqDto);

    @Operation(summary = "실시간 사진 랭킹 조회", description = "전체 사진 득표수 랭킹을 조회합니다. (15일 15시~19시 비공개)")
    ResponseEntity<ApiResponse<List<PhotoRankResDto>>> getStudentRank();
}
