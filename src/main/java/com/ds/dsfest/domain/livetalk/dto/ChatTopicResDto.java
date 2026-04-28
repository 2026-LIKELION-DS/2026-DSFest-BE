package com.ds.dsfest.domain.livetalk.dto;

import com.ds.dsfest.domain.livetalk.entity.ChatTopic;

public record ChatTopicResDto(
    Long id,
    String content,
    com.ds.dsfest.domain.livetalk.constant.ChatTopicType topicType,
    Long artistId) {
  public static ChatTopicResDto from(ChatTopic chatTopic) {
    return new ChatTopicResDto(
        chatTopic.getId(),
        chatTopic.getContent(),
        chatTopic.getTopicType(),
        chatTopic.getArtist() != null ? chatTopic.getArtist().getId() : null);
  }
}
