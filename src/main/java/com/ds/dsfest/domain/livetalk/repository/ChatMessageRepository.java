package com.ds.dsfest.domain.livetalk.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.livetalk.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

  List<ChatMessage> findTop50ByOrderByCreatedAtDesc();

  long countByCreatedAtAfterAndGuestUser_UuidNot(LocalDateTime lastReadAt, UUID guestUuid);

  long countByGuestUser_UuidNot(UUID guestUuid);
}
