package com.ds.dsfest.domain.notice.dto;

import java.time.LocalDateTime;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;
import com.ds.dsfest.domain.notice.entity.Notice;

public record NoticeListItemResDto(
    Long id,
    String title,
    NoticeCategory category,
    boolean urgent,
    LocalDateTime createdAt,
    int viewCount) {

  public static NoticeListItemResDto from(Notice notice) {
    return new NoticeListItemResDto(
        notice.getId(),
        notice.getTitle(),
        notice.getCategory(),
        notice.isUrgent(),
        notice.getCreatedAt(),
        notice.getViewCount());
  }
}
