package com.ds.dsfest.global.response;

import com.ds.dsfest.global.exception.model.BaseErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiResponse<T>(
    @JsonProperty("isSuccess") boolean isSuccess, String code, String message, T result) {

  private static final String SUCCESS_CODE = "COMMON200";
  private static final String SUCCESS_MESSAGE = "요청에 성공하였습니다.";

  public static <T> ApiResponse<T> onSuccess(T result) {
    return new ApiResponse<>(true, SUCCESS_CODE, SUCCESS_MESSAGE, result);
  }

  public static ApiResponse<Void> onSuccess() {
    return new ApiResponse<>(true, SUCCESS_CODE, SUCCESS_MESSAGE, null);
  }

  public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode) {
    return new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null);
  }

  public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, T data) {
    return new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), data);
  }
}
