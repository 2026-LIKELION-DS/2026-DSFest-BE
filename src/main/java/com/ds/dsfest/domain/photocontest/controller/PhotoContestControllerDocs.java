package com.ds.dsfest.domain.photocontest.controller;

import com.ds.dsfest.domain.photocontest.dto.PhotoContestStatusResDto;
import com.ds.dsfest.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Photo Contest", description = "사진 콘테스트 관련 API")
public interface PhotoContestControllerDocs {

    @Operation(summary = "사진 콘테스트 상태 조회", description = "현재 콘테스트 상태(ACCEPTING, VOTING, ENDED)를 반환합니다.")
    ResponseEntity<ApiResponse<PhotoContestStatusResDto>> getContestStatus();
}
