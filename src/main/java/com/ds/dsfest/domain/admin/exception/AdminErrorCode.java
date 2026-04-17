package com.ds.dsfest.domain.admin.exception;

import org.springframework.http.HttpStatus;

import com.ds.dsfest.global.exception.model.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminErrorCode implements BaseErrorCode {
  ADMIN_LOGIN_FAILED("ADMIN001", "아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED),
  ADMIN_TOKEN_INVALID("ADMIN002", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
  ADMIN_TOKEN_EXPIRED("ADMIN003", "만료된 토큰입니다.", HttpStatus.UNAUTHORIZED);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
