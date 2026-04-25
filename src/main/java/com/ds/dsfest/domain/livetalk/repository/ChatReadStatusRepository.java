package com.ds.dsfest.domain.livetalk.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ds.dsfest.domain.livetalk.entity.ChatReadStatus;

public interface ChatReadStatusRepository extends JpaRepository<ChatReadStatus, Long> {

  Optional<ChatReadStatus> findByGuestUuid(String guestUuid);

  @Modifying(clearAutomatically = true)
  @Query(
      value =
          """
    INSERT INTO chat_read_status (guest_uuid, last_read_at)
    VALUES (:guestUuid, :lastReadAt)
    ON DUPLICATE KEY UPDATE last_read_at = VALUES(last_read_at)
    """,
      nativeQuery = true)
  void upsertReadStatus(
      @Param("guestUuid") String guestUuid, @Param("lastReadAt") LocalDateTime lastReadAt);
}
