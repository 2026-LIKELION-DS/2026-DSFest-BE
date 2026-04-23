package com.ds.dsfest.domain.livetalk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.ds.dsfest.domain.user.entity.GuestUser;
import com.ds.dsfest.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_messages")
public class ChatMessage extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "guest_uuid", referencedColumnName = "uuid", nullable = false)
  private GuestUser guestUser;

  @Column(nullable = false, length = 500)
  private String content;

  private ChatMessage(GuestUser guestUser, String content) {
    this.guestUser = guestUser;
    this.content = content;
  }

  public static ChatMessage create(GuestUser guestUser, String content) {
    return new ChatMessage(guestUser, content);
  }
}
