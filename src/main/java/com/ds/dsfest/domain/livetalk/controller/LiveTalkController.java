package com.ds.dsfest.domain.livetalk.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ds.dsfest.domain.livetalk.dto.ChatMessageResDto;
import com.ds.dsfest.domain.livetalk.service.LiveTalkService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/livetalk")
public class LiveTalkController {

  private final LiveTalkService liveTalkService;

  // 메시지 목록
  @GetMapping("/messages")
  public ResponseEntity<ApiResponse<List<ChatMessageResDto>>> getRecentMessages() {
    return ResponseEntity.ok(ApiResponse.onSuccess(liveTalkService.getRecentMessages()));
  }

  // 읽음 처리
  @PatchMapping("/read")
  public ResponseEntity<ApiResponse<Void>> markAsRead(@RequestParam String guestUuid) {
    liveTalkService.markAsRead(guestUuid);
    return ResponseEntity.ok(ApiResponse.onSuccess(null));
  }

  // 안 읽은 메시지 수
  @GetMapping("/unread-count")
  public ResponseEntity<ApiResponse<Long>> getUnreadCount(@RequestParam String guestUuid) {
    return ResponseEntity.ok(ApiResponse.onSuccess(liveTalkService.getUnreadCount(guestUuid)));
  }
}
