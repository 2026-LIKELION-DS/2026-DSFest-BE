package com.ds.dsfest.domain.livetalk.dto;

import java.time.LocalDateTime;

import com.ds.dsfest.domain.livetalk.entity.ChatMessage;

public record ChatMessageResDto(
    Long messageId, String senderGuestUuid, String content, LocalDateTime createdAt) {
  public static ChatMessageResDto from(ChatMessage chatMessage) {
    return new ChatMessageResDto(
        chatMessage.getId(),
        chatMessage.getGuestUser().getUuid().toString(),
        chatMessage.getContent(),
        chatMessage.getCreatedAt());
  }
}
