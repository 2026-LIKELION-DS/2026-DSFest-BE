package com.ds.dsfest.domain.livetalk.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.livetalk.entity.ChatTopic;

public interface ChatTopicRepository extends JpaRepository<ChatTopic, Long> {

  @EntityGraph(attributePaths = {"artist"})
  Optional<ChatTopic>
      findFirstByStartTimeLessThanEqualAndEndTimeGreaterThanEqualOrderByStartTimeDesc(
          LocalDateTime now, LocalDateTime now2);
}
