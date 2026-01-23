package com.resona.domain.member.exception;

import com.resona.global.exception.GlobalException;
import com.resona.global.response.BaseCode;
import com.resona.global.response.ErrorCode;

public class MemberException extends GlobalException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
