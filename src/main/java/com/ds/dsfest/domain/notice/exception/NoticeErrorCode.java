package com.ds.dsfest.domain.notice.exception;

import org.springframework.http.HttpStatus;

import com.ds.dsfest.global.exception.model.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeErrorCode implements BaseErrorCode {
  NOTICE_NOT_FOUND("NOTICE001", "공지사항을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
