package com.ds.dsfest.domain.user.exception;

import org.springframework.http.HttpStatus;

import com.ds.dsfest.global.exception.model.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
  GUEST_NOT_FOUND("USER_001", "존재하지 않는 게스트 사용자입니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
