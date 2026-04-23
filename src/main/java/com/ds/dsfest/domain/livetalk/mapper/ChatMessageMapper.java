package com.ds.dsfest.domain.livetalk.mapper;

import org.springframework.stereotype.Component;

import com.ds.dsfest.domain.livetalk.dto.ChatMessageResDto;
import com.ds.dsfest.domain.livetalk.entity.ChatMessage;

@Component
public class ChatMessageMapper {

  public ChatMessageResDto toResDto(ChatMessage chatMessage) {
    return ChatMessageResDto.from(chatMessage);
  }
}
