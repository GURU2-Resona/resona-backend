package com.resona.global.response;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum SuccessCode implements BaseCode {

  // common
  OK(HttpStatus.OK, "OK200", "요청에 성공하였습니다."),
  // user
  USER_UPDATE_OK(HttpStatus.OK, "USER2001", "사용자 정보가 수정되었습니다."),
  NICKNAME_SAVE_OK(HttpStatus.OK, "MEMBER200", "닉네임이 저장되었습니다."),
  MEMBER_PROFILE_GET_OK(HttpStatus.OK, "MEMBER2001", "회원 프로필 조회에 성공하였습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  @Override
  public HttpStatus getHttpStatus() {
    return status;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
