package com.ds.dsfest.domain.livetalk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.livetalk.entity.ChatReadStatus;

public interface ChatReadStatusRepository extends JpaRepository<ChatReadStatus, Long> {

  Optional<ChatReadStatus> findByGuestUuid(String guestUuid);
}
