package com.ds.dsfest.domain.notice.controller;

import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ds.dsfest.domain.notice.dto.NoticeCreateReqDto;
import com.ds.dsfest.domain.notice.dto.NoticeDetailResDto;
import com.ds.dsfest.domain.notice.dto.NoticeListItemResDto;
import com.ds.dsfest.domain.notice.dto.NoticeUpdateReqDto;
import com.ds.dsfest.domain.notice.service.NoticeService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notices")
public class AdminNoticeController implements AdminNoticeControllerDocs {

  private final NoticeService noticeService;

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<ApiResponse<NoticeDetailResDto>> createNotice(
      @RequestPart("data") @Valid NoticeCreateReqDto req,
      @RequestPart(value = "images", required = false) List<MultipartFile> images)
      throws IOException {
    return ResponseEntity.ok(ApiResponse.onSuccess(noticeService.createNotice(req, images)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<NoticeListItemResDto>>> getNoticeList() {
    return ResponseEntity.ok(ApiResponse.onSuccess(noticeService.getNoticeList()));
  }

  @GetMapping("/{noticeId}")
  public ResponseEntity<ApiResponse<NoticeDetailResDto>> getNoticeDetail(
      @PathVariable Long noticeId) {
    return ResponseEntity.ok(ApiResponse.onSuccess(noticeService.getNoticeDetail(noticeId)));
  }

  @PutMapping(value = "/{noticeId}", consumes = "multipart/form-data")
  public ResponseEntity<ApiResponse<NoticeDetailResDto>> updateNotice(
      @PathVariable Long noticeId,
      @RequestPart("data") @Valid NoticeUpdateReqDto req,
      @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages)
      throws IOException {
    return ResponseEntity.ok(
        ApiResponse.onSuccess(noticeService.updateNotice(noticeId, req, newImages)));
  }

  @DeleteMapping("/{noticeId}")
  public ResponseEntity<ApiResponse<Void>> deleteNotice(@PathVariable Long noticeId) {
    noticeService.deleteNotice(noticeId);
    return ResponseEntity.ok(ApiResponse.onSuccess());
  }

  @PatchMapping("/urgent/clear")
  public ResponseEntity<ApiResponse<Void>> clearAllUrgent() {
    noticeService.clearAllUrgent();
    return ResponseEntity.ok(ApiResponse.onSuccess());
  }
}
