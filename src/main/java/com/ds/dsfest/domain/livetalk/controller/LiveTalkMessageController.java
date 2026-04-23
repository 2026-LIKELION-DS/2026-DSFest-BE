package com.ds.dsfest.domain.livetalk.controller;

import jakarta.validation.Valid;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.ds.dsfest.domain.livetalk.dto.ChatMessageResDto;
import com.ds.dsfest.domain.livetalk.dto.ChatMessageSendReqDto;
import com.ds.dsfest.domain.livetalk.service.LiveTalkService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LiveTalkMessageController {

  private final LiveTalkService liveTalkService;
  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/livetalk.send")
  public void sendMessage(@Valid @Payload ChatMessageSendReqDto request) {
    ChatMessageResDto response =
        liveTalkService.saveMessage(request.guestUuid(), request.content());

    messagingTemplate.convertAndSend("/topic/livetalk", response);
  }
}
