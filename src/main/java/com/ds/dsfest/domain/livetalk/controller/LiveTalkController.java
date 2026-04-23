package com.ds.dsfest.domain.livetalk.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds.dsfest.domain.livetalk.dto.ChatMessageResDto;
import com.ds.dsfest.domain.livetalk.service.LiveTalkService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/livetalk")
public class LiveTalkController {

  private final LiveTalkService liveTalkService;

  @GetMapping("/messages")
  public ResponseEntity<ApiResponse<List<ChatMessageResDto>>> getRecentMessages() {
    return ResponseEntity.ok(ApiResponse.onSuccess(liveTalkService.getRecentMessages()));
  }
}
