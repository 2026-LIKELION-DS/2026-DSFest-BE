package com.ds.dsfest.domain.notice.controller;

import jakarta.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.ds.dsfest.domain.notice.dto.NoticeSearchResDto;
import com.ds.dsfest.domain.notice.dto.UrgentNoticeResDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notice", description = "공지사항 사용자 API")
public interface NoticeControllerDocs {

  @Operation(
      summary = "긴급공지 조회",
      description = "긴급 플래그가 설정된 공지 중 가장 최신 1건의 id와 제목을 반환합니다. 긴급공지가 없으면 result가 null입니다.")
  ResponseEntity<ApiResponse<UrgentNoticeResDto>> getUrgentNotice();

  @Operation(
      summary = "공지 검색",
      description = "keyword로 제목·본문을 검색합니다. 결과가 없으면 results는 빈 배열, recommended에 조회수 상위 3개가 담깁니다.")
  ResponseEntity<ApiResponse<NoticeSearchResDto>> searchNotices(
      @RequestParam @NotBlank String keyword);
}
