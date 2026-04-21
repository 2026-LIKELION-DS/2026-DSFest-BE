package com.ds.dsfest.domain.booth.exception;

import org.springframework.http.HttpStatus;

import com.ds.dsfest.global.exception.model.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoothErrorCode implements BaseErrorCode {
  BOOTH_NOT_FOUND("BOOTH001", "부스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  INVALID_FESTIVAL_DAY("BOOTH002", "유효하지 않은 축제 일자입니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
