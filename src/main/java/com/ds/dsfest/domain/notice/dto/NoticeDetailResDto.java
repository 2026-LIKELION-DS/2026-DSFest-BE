package com.ds.dsfest.domain.notice.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;
import com.ds.dsfest.domain.notice.entity.Notice;

public record NoticeDetailResDto(
    Long id,
    String title,
    NoticeCategory category,
    boolean urgent,
    String content,
    List<String> imageUrls,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    int viewCount) {

  public static NoticeDetailResDto from(Notice notice) {
    List<String> imageUrls = notice.getImages().stream().map(image -> image.getImageUrl()).toList();
    return new NoticeDetailResDto(
        notice.getId(),
        notice.getTitle(),
        notice.getCategory(),
        notice.isUrgent(),
        notice.getContent(),
        imageUrls,
        notice.getCreatedAt(),
        notice.getUpdatedAt(),
        notice.getViewCount());
  }
}
