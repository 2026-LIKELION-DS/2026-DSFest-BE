package com.ds.dsfest.domain.livetalk.dto;

import com.ds.dsfest.domain.livetalk.constant.ChatTopicType;
import com.ds.dsfest.domain.livetalk.entity.ChatTopic;

public record ChatTopicResDto(
    Long id, String title, String subtitle, ChatTopicType topicType, Long artistId) {

  public static ChatTopicResDto from(ChatTopic topic) {
    return new ChatTopicResDto(
        topic.getId(),
        topic.getTitle(),
        topic.getSubtitle(),
        topic.getTopicType(),
        topic.getArtist() != null ? topic.getArtist().getId() : null);
  }
}
