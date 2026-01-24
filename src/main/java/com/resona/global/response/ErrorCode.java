package com.resona.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  // Common (공통적으로 쓸 기본 에러들)
  _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
  _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
  _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
  _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

  // 유효성 검사 (RequestBody 필드 에러 등)
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON4001", "유효하지 않은 입력값입니다."),

  // post
  CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY404", "해당 카테고리를 찾을 수 없습니다."),
  SCENE_NOT_FOUND(HttpStatus.NOT_FOUND, "SCENE404", "해당 상황 태그를 찾을 수 없습니다."),
  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "post404", "해당 추천글을 찾을 수 없습니다."),
  // Member
  NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "해당 사용자를 찾지 못했습니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}
