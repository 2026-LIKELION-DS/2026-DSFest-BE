package com.ds.dsfest.domain.livetalk.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.livetalk.dto.ChatTopicResDto;
import com.ds.dsfest.domain.livetalk.repository.ChatTopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatTopicService {

  private final ChatTopicRepository chatTopicRepository;

  public ChatTopicResDto getCurrentTopic() {
    LocalDateTime now = LocalDateTime.now();

    return chatTopicRepository
        .findFirstByStartTimeLessThanEqualAndEndTimeGreaterThanEqualOrderByStartTimeDesc(now, now)
        .map(ChatTopicResDto::from)
        .orElse(null);
  }
}
