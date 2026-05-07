package com.ds.dsfest.domain.livetalk.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ds.dsfest.domain.livetalk.dto.ChatMessageResDto;
import com.ds.dsfest.domain.livetalk.dto.ChatTopicResDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "LiveTalk", description = "라이브톡 관련 API")
public interface LiveTalkControllerDocs {

  @Operation(summary = "최근 메시지 목록 조회", description = "라이브톡의 최근 메시지 목록을 조회합니다.")
  ResponseEntity<ApiResponse<List<ChatMessageResDto>>> getRecentMessages();

  @Operation(summary = "이전 메시지 조회", description = "messageId를 기준으로 해당 메시지보다 이전에 작성된 메시지 목록을 조회합니다.")
  ResponseEntity<ApiResponse<List<ChatMessageResDto>>> getMessagesBefore(Long messageId);

  @Operation(summary = "읽음 처리", description = "guestUuid 기준으로 라이브톡 메시지를 읽음 처리합니다.")
  ResponseEntity<ApiResponse<Void>> markAsRead(String guestUuid);

  @Operation(summary = "안 읽은 메시지 수 조회", description = "guestUuid 기준으로 아직 읽지 않은 라이브톡 메시지 수를 조회합니다.")
  ResponseEntity<ApiResponse<Long>> getUnreadCount(String guestUuid);

  @Operation(summary = "현재 대화 주제 조회", description = "현재 시각 기준으로 진행 중인 라이브톡 대화 주제를 조회합니다.")
  ResponseEntity<ApiResponse<ChatTopicResDto>> getCurrentTopic();
}
