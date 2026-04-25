package com.ds.dsfest.domain.photocontest.controller;

import com.ds.dsfest.domain.photocontest.dto.PhotoRankResDto;
import com.ds.dsfest.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Admin - 사진 콘테스트", description = "총학생회 전용 사진 콘테스트 투표 결과 관리 API")
@SecurityRequirement(name = "BearerAuth")
public interface AdminPhotoContestControllerDocs {

    @Operation(
        summary = "투표 결과 실시간 집계 조회",
        description = "총학생회 관리자가 테마별 사진 득표수 랭킹을 조회합니다."
    )
    ResponseEntity<ApiResponse<Map<String, List<PhotoRankResDto>>>> getVoteResults();
}
