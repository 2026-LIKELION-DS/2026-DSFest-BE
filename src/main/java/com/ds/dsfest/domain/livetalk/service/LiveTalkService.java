package com.ds.dsfest.domain.livetalk.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.livetalk.dto.ChatMessageResDto;
import com.ds.dsfest.domain.livetalk.entity.ChatMessage;
import com.ds.dsfest.domain.livetalk.mapper.ChatMessageMapper;
import com.ds.dsfest.domain.livetalk.repository.ChatMessageRepository;
import com.ds.dsfest.domain.user.entity.GuestUser;
import com.ds.dsfest.domain.user.exception.UserErrorCode;
import com.ds.dsfest.domain.user.repository.GuestUserRepository;
import com.ds.dsfest.global.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LiveTalkService {

  private final ChatMessageRepository chatMessageRepository;
  private final GuestUserRepository guestUserRepository;
  private final ChatMessageMapper chatMessageMapper;

  @Transactional(readOnly = true)
  public List<ChatMessageResDto> getRecentMessages() {
    List<ChatMessage> messages = chatMessageRepository.findTop50ByOrderByCreatedAtDesc();

    return messages.stream()
        .sorted((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()))
        .map(chatMessageMapper::toResDto)
        .toList();
  }

  public ChatMessageResDto saveMessage(String guestUuid, String content) {
    UUID uuid = parseUuid(guestUuid);

    GuestUser guestUser =
        guestUserRepository
            .findById(uuid)
            .orElseThrow(() -> new CustomException(UserErrorCode.GUEST_NOT_FOUND));

    String normalizedContent = content == null ? "" : content.trim();
    if (normalizedContent.isBlank()) {
      throw new IllegalArgumentException("메시지는 비어 있을 수 없습니다.");
    }

    ChatMessage chatMessage = ChatMessage.create(guestUser, normalizedContent);
    ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

    return chatMessageMapper.toResDto(savedMessage);
  }

  private UUID parseUuid(String guestUuid) {
    try {
      return UUID.fromString(guestUuid);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("올바르지 않은 guestUuid 형식입니다.");
    }
  }
}
