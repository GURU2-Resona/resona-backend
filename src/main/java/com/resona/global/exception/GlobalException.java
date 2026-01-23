package com.resona.global.exception;

import com.resona.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

  private final ErrorCode errorCode;

  public GlobalException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
