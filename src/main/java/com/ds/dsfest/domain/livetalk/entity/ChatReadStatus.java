package com.ds.dsfest.domain.livetalk.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_read_status")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatReadStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String guestUuid;

  @Column(nullable = false)
  private LocalDateTime lastReadAt;

  public ChatReadStatus(String guestUuid, LocalDateTime lastReadAt) {
    this.guestUuid = guestUuid;
    this.lastReadAt = lastReadAt;
  }

  public void updateLastReadAt(LocalDateTime lastReadAt) {
    this.lastReadAt = lastReadAt;
  }
}
