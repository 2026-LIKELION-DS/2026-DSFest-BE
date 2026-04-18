package com.ds.dsfest.domain.notice.dto;

import com.ds.dsfest.domain.notice.entity.Notice;

public record UrgentNoticeResDto(Long id, String title) {

  public static UrgentNoticeResDto from(Notice notice) {
    return new UrgentNoticeResDto(notice.getId(), notice.getTitle());
  }
}
