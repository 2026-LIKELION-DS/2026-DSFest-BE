package com.ds.dsfest.domain.livetalk.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Comparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.livetalk.dto.ChatMessageResDto;
import com.ds.dsfest.domain.livetalk.entity.ChatMessage;
import com.ds.dsfest.domain.livetalk.mapper.ChatMessageMapper;
import com.ds.dsfest.domain.livetalk.repository.ChatMessageRepository;
import com.ds.dsfest.domain.livetalk.repository.ChatReadStatusRepository;
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
  private final ChatReadStatusRepository chatReadStatusRepository;

  @Transactional(readOnly = true)
  public List<ChatMessageResDto> getRecentMessages() {
    return chatMessageRepository.findTop50ByOrderByCreatedAtDesc().stream()
        .sorted(Comparator.comparing(ChatMessage::getCreatedAt).thenComparing(ChatMessage::getId))
        .map(chatMessageMapper::toResDto)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<ChatMessageResDto> getMessagesBefore(Long messageId) {
    return chatMessageRepository.findTop50ByIdLessThanOrderByIdDesc(messageId).stream()
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
    if (normalizedContent.length() > 500) {
      throw new IllegalArgumentException("메시지는 500자를 초과할 수 없습니다.");
    }

    ChatMessage chatMessage = ChatMessage.create(guestUser, normalizedContent);
    ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

    return chatMessageMapper.toResDto(savedMessage);
  }

  private UUID parseUuid(String guestUuid) {
    if (guestUuid == null || guestUuid.isBlank()) {
      throw new IllegalArgumentException("guestUuid는 필수값입니다.");
    }
    try {
      return UUID.fromString(guestUuid.trim());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("올바르지 않은 guestUuid 형식입니다.");
    }
  }

  // ✅ 읽음 처리
  @Transactional
  public void markAsRead(String guestUuid) {
    String normalizedGuestUuid = parseUuid(guestUuid).toString();

    chatReadStatusRepository.upsertReadStatus(normalizedGuestUuid, LocalDateTime.now());
  }

  // ✅ 안 읽은 메시지 수
  @Transactional(readOnly = true)
  public long getUnreadCount(String guestUuid) {
    UUID uuid = parseUuid(guestUuid);
    String normalizedGuestUuid = uuid.toString();

    return chatReadStatusRepository
        .findByGuestUuid(normalizedGuestUuid)
        .map(
            status ->
                chatMessageRepository.countByCreatedAtAfterAndGuestUser_UuidNot(
                    status.getLastReadAt(), uuid))
        .orElseGet(() -> chatMessageRepository.countByGuestUser_UuidNot(uuid));
  }
}
