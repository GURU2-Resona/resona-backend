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
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON4001", "유효하지 않은 입력값입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
