package com.resona.domain.member.dto;

import com.resona.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResult {
    private final Member member;
    private final boolean isNewUser;
}

