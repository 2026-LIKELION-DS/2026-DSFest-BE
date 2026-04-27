package com.ds.dsfest.domain.notice.controller;

import com.ds.dsfest.domain.notice.dto.NoticeListItemResDto;
import com.ds.dsfest.domain.notice.dto.NoticeSearchResDto;
import com.ds.dsfest.domain.notice.dto.UrgentNoticeResDto;
import com.ds.dsfest.domain.notice.service.NoticeService;
import com.ds.dsfest.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController implements NoticeControllerDocs {

  private final NoticeService noticeService;

  /**
   * Retrieves the list of notices.
   *
   * @return a ResponseEntity with status 200 (OK) containing an ApiResponse that wraps the list of notice list items.
   */
  @GetMapping
  @Override
  public ResponseEntity<ApiResponse<List<NoticeListItemResDto>>> getNoticeList() {
    return ResponseEntity.ok(ApiResponse.onSuccess(noticeService.getNoticeList()));
  }

  /**
   * Retrieve the current urgent notice.
   *
   * @return ResponseEntity with HTTP 200 whose body is an ApiResponse containing the urgent notice DTO
   */
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
