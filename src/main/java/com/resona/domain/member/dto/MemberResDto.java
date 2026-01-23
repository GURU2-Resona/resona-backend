package com.resona.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberResDto {

    @Getter
    @AllArgsConstructor
    public static class Tokens{
        private String accessToken;
        private String refreshToken;
    }
}
