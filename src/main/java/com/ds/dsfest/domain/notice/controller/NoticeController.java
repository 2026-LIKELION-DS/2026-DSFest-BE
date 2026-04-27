package com.ds.dsfest.domain.notice.controller;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;
import com.ds.dsfest.domain.notice.dto.NoticeDetailResDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController implements NoticeControllerDocs {

  private final NoticeService noticeService;

  @GetMapping
  @Override
  public ResponseEntity<ApiResponse<List<NoticeListItemResDto>>> getNoticeList() {
    return ResponseEntity.ok(ApiResponse.onSuccess(noticeService.getNoticeList()));
  }

  @GetMapping("/category")
  @Override
  public ResponseEntity<ApiResponse<List<NoticeListItemResDto>>> getNoticesByCategory(
      @RequestParam NoticeCategory category
  ) {
     return ResponseEntity.ok(ApiResponse.onSuccess(noticeService.getNoticeListByCategory(category)));
  }

  @GetMapping("/{noticeId}")
  @Override
  public ResponseEntity<ApiResponse<NoticeDetailResDto>> getNoticeDetail(@PathVariable Long noticeId) {
      return ResponseEntity.ok(ApiResponse.onSuccess(noticeService.getNoticeDetail(noticeId)));
  }

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
