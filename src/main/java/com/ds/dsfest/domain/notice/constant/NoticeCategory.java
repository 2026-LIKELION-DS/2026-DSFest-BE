package com.ds.dsfest.domain.notice.constant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공지 카테고리")
public enum NoticeCategory {
  @Schema(description = "이벤트")
  EVENT,
  @Schema(description = "공연")
  PERFORMANCE,
  @Schema(description = "기타")
  ETC,
  @Schema(description = "안내")
  NOTICE
}
