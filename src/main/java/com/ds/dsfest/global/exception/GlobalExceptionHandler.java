package com.ds.dsfest.global.exception;

import com.ds.dsfest.global.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /** 비즈니스 로직 예외 */
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
    log.warn("CustomException: {}", e.getMessage());
    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(ApiResponse.onFailure(e.getErrorCode()));
  }

  /**
   * @Valid 검증 실패 - 필드 에러 메시지 모아서 반환
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<String>> handleValidationException(
      MethodArgumentNotValidException e) {
    String errorMessage =
        e.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "));
    log.warn("ValidationException: {}", errorMessage);
    return ResponseEntity.status(GlobalErrorCode.INVALID_INPUT.getStatus())
        .body(ApiResponse.onFailure(GlobalErrorCode.INVALID_INPUT, errorMessage));
  }

  /**
   * @Validated 파라미터 검증 실패
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse<String>> handleConstraintViolationException(
      ConstraintViolationException e) {
    String errorMessage =
        e.getConstraintViolations().stream()
            .map(v -> v.getMessage())
            .collect(Collectors.joining(", "));
    log.warn("ConstraintViolationException: {}", errorMessage);
    return ResponseEntity.status(GlobalErrorCode.INVALID_INPUT.getStatus())
        .body(ApiResponse.onFailure(GlobalErrorCode.INVALID_INPUT, errorMessage));
  }
  /**
   * enum 변환 등 parameter 타입 변환 실패시
   * (ex. category=UNKNOWN 처럼 enum 바인딩 실패)
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
      log.warn("MethodArgumentTypeMismatchException: {}", e.getMessage());
      return ResponseEntity.status(GlobalErrorCode.INVALID_INPUT.getStatus())
          .body(ApiResponse.onFailure(GlobalErrorCode.INVALID_INPUT, "잘못된 입력 값입니다: " + e.getValue()));
  }

  /** 존재하지 않는 경로 (favicon.ico 등 브라우저 기본 요청 포함) */
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(
      NoResourceFoundException e) {
    log.debug("NoResourceFoundException: {}", e.getMessage());
    return ResponseEntity.status(GlobalErrorCode.NOT_FOUND.getStatus())
        .body(ApiResponse.onFailure(GlobalErrorCode.NOT_FOUND));
  }

  /** 허용되지 않는 HTTP 메서드 */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse<Void>> handleMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    log.warn("MethodNotSupportedException: {}", e.getMessage());
    return ResponseEntity.status(GlobalErrorCode.METHOD_NOT_ALLOWED.getStatus())
        .body(ApiResponse.onFailure(GlobalErrorCode.METHOD_NOT_ALLOWED));
  }

  /** 핸들링되지 않은 예외 */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
    log.error("UnhandledException: {}", e.getMessage(), e);
    return ResponseEntity.status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
        .body(ApiResponse.onFailure(GlobalErrorCode.INTERNAL_SERVER_ERROR));
  }
}
