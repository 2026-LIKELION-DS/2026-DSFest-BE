package com.ds.dsfest.domain.livetalk.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ds.dsfest.domain.livetalk.dto.ChatMessageResDto;
import com.ds.dsfest.domain.livetalk.dto.ChatTopicResDto;
import com.ds.dsfest.domain.livetalk.service.ChatTopicService;
import com.ds.dsfest.domain.livetalk.service.LiveTalkService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/livetalk")
public class LiveTalkController implements LiveTalkControllerDocs {

  private final LiveTalkService liveTalkService;
  private final ChatTopicService chatTopicService;

  // 메시지 목록
  @Override
  @GetMapping("/messages")
  public ResponseEntity<ApiResponse<List<ChatMessageResDto>>> getRecentMessages() {
    return ResponseEntity.ok(ApiResponse.onSuccess(liveTalkService.getRecentMessages()));
  }

  // 이전 메시지
  @Override
  @GetMapping("/messages/before")
  public ResponseEntity<ApiResponse<List<ChatMessageResDto>>> getMessagesBefore(
      @RequestParam Long messageId) {
    return ResponseEntity.ok(ApiResponse.onSuccess(liveTalkService.getMessagesBefore(messageId)));
  }

  // 읽음 처리
  @Override
  @PatchMapping("/read")
  public ResponseEntity<ApiResponse<Void>> markAsRead(@RequestParam String guestUuid) {
    liveTalkService.markAsRead(guestUuid);
    return ResponseEntity.ok(ApiResponse.onSuccess(null));
  }

  // 안 읽은 메시지 수
  @Override
  @GetMapping("/unread-count")
  public ResponseEntity<ApiResponse<Long>> getUnreadCount(@RequestParam String guestUuid) {
    return ResponseEntity.ok(ApiResponse.onSuccess(liveTalkService.getUnreadCount(guestUuid)));
  }

  // 대화 주제
  @Override
  @GetMapping("/topics/current")
  public ResponseEntity<ApiResponse<ChatTopicResDto>> getCurrentTopic() {
    return ResponseEntity.ok(ApiResponse.onSuccess(chatTopicService.getCurrentTopic()));
  }
}
