package com.ds.dsfest.domain.notice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ds.dsfest.domain.notice.dto.NoticeSearchResDto;
import com.ds.dsfest.domain.notice.dto.UrgentNoticeResDto;
import com.ds.dsfest.domain.notice.service.NoticeService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController implements NoticeControllerDocs {

  private final NoticeService noticeService;

  @GetMapping("/urgent")
  public ResponseEntity<ApiResponse<UrgentNoticeResDto>> getUrgentNotice() {
    return ResponseEntity.ok(ApiResponse.onSuccess(noticeService.getUrgentNotice()));
  }

  @GetMapping("/search")
  public ResponseEntity<ApiResponse<NoticeSearchResDto>> searchNotices(
      @RequestParam String keyword) {
    return ResponseEntity.ok(ApiResponse.onSuccess(noticeService.searchNotices(keyword)));
  }
}
