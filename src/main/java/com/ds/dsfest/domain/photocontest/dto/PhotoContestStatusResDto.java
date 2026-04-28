package com.ds.dsfest.domain.photocontest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "사진 콘테스트 상태 응답 DTO")
public record PhotoContestStatusResDto(
    @Schema(description = "콘테스트 상태 (ACCEPTING: 응모 중, VOTING: 투표 중, ENDED: 종료됨)", example = "ACCEPTING")
    String status,

    @Schema(description = "현재 상태의 시작 시간", example = "2026-05-13T09:00:00")
    LocalDateTime startTime,

    @Schema(description = "현재 상태의 마감 시간 (카운트다운용)", example = "2026-05-14T20:00:00")
    LocalDateTime endTime
) {
}
