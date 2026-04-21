package com.ds.dsfest.domain.artist.exception;

import org.springframework.http.HttpStatus;

import com.ds.dsfest.global.exception.model.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArtistErrorCode implements BaseErrorCode {
  ARTIST_NOT_FOUND("ARTIST001", "해당 아티스트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
