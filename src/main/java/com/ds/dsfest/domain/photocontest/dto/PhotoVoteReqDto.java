package com.ds.dsfest.domain.photocontest.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사진 콘테스트 투표 요청 DTO")
public record PhotoVoteReqDto(
    @Schema(description = "학번 (8~9자리 숫자)", example = "20260000")
        @NotBlank(message = "학번을 입력해주세요.")
        @Pattern(regexp = "^[0-9]{8,9}$", message = "올바른 학번 형식이 아닙니다.")
        String studentId,
    @Schema(description = "이름", example = "박덕우") @NotBlank(message = "이름을 입력해주세요.")
        String studentName,
    @Schema(description = "선택한 사진 ID 목록 (반드시 3개)", example = "[1, 8, 15]")
        @NotNull(message = "투표할 사진을 선택해주세요.")
        @Size(min = 3, max = 3, message = "각 주제별로 1장씩, 총 3장을 선택해야 합니다.")
        List<@NotNull(message = "사진 ID는 null일 수 없습니다.") Long> photoEntryIds) {}
