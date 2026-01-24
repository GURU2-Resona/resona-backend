package com.resona.global.exception;

import com.resona.global.response.ApiResponse;
import com.resona.global.response.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 1. 우리가 직접 만든 커스텀 예외 처리 (비즈니스 로직 에러)
  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<ApiResponse<Void>> handleGlobalException(GlobalException e) {
    log.warn("Business Exception: {}", e.getMessage());
    ErrorCode errorCode = e.getErrorCode();
    return ResponseEntity.status(errorCode.getHttpStatus())
        .body(ApiResponse.onFailure(errorCode, null));
  }

  // 2. @Valid 유효성 검사 실패 처리 (Request Body 에러)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<String>> handleValidationException(
      MethodArgumentNotValidException e) {
    // 첫 번째 에러 메시지만 가져오기
    String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    log.warn("Validation Error: {}", errorMessage);

    return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
        .body(ApiResponse.onFailure(ErrorCode.INVALID_INPUT_VALUE, errorMessage));
  }

  // 3. 파라미터 타입 불일치 에러
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiResponse<String>> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException e) {
    log.warn("Type Mismatch Error: {}", e.getMessage());

    // 어떤 파라미터가 잘못되었는지
    String errorMessage =
        String.format("파라미터 '%s'의 값이 잘못되었습니다. 입력된 값: (%s)", e.getName(), e.getValue());

    return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
        .body(ApiResponse.onFailure(ErrorCode.INVALID_INPUT_VALUE, errorMessage));
  }

  // 4. 그 외 알 수 없는 모든 서버 에러 처리 (500)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
    log.error("Unhandled Exception: ", e);

    return ResponseEntity.status(ErrorCode._INTERNAL_SERVER_ERROR.getHttpStatus())
        .body(ApiResponse.onFailure(ErrorCode._INTERNAL_SERVER_ERROR, e.getMessage()));
  }
}
