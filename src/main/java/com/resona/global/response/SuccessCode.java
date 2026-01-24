package com.resona.global.response;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum SuccessCode implements BaseCode {

    // common
    OK(HttpStatus.OK, "OK200", "요청에 성공하였습니다."),

    // challenge wait
    CHALLENGE_WAIT_REGISTER_OK(HttpStatus.OK, "WAIT2001", "챌린지 대기 신청이 완료되었습니다."),
    CHALLENGE_WAIT_CANCEL_OK(HttpStatus.OK, "WAIT2002", "챌린지 대기 신청이 취소되었습니다."),

    // comment
    COMMENT_POST_OK(HttpStatus.OK, "COMMENT2001","댓글이 작성되었습니다."),
    COMMENT_UPDATE_OK(HttpStatus.OK, "COMMENT2002", "댓글이 수정되었습니다."),
    COMMENT_DELETE_OK(HttpStatus.OK, "COMMENT2003", "댓글이 삭제되었습니다."),
    COMMENT_ADOPT_OK(HttpStatus.OK, "COMMENT2004", "댓글이 채택되었습니다."),

    // verification
    VERIFICATION_POST_OK(HttpStatus.CREATED, "VERIFICATION2001", "인증글이 작성되었습니다."),
    VERIFICATION_DELETE_OK(HttpStatus.OK, "VERIFICATION2002", "인증글이 삭제되었습니다."),
    VERIFICATION_UPDATE_OK(HttpStatus.OK, "VERIFICATION2003", "인증글이 수정되었습니다."),

    //user
    USER_UPDATE_OK(HttpStatus.OK, "USER2001", "사용자 정보가 수정되었습니다."),
    NICKNAME_SAVE_OK(HttpStatus.OK ,"MEMBER200", "닉네임이 저장되었습니다."),
    MEMBER_PROFILE_GET_OK(HttpStatus.OK, "MEMBER2001", "회원 프로필 조회에 성공하였습니다."),
    USER_LOGIN_OK(HttpStatus.OK ,"MEMBER200_2", "로그인에 성공했습니다..");

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
