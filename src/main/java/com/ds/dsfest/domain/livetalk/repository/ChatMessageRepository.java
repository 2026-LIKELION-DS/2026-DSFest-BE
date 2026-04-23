package com.ds.dsfest.domain.livetalk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.livetalk.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

  List<ChatMessage> findTop50ByOrderByCreatedAtDesc();
}
