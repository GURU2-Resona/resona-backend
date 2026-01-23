package com.resona.domain.member.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class KakaoUserInfo {

    private final String providerId;
    private final String profileImageUrl;

    private KakaoUserInfo(String providerId, String profileImageUrl) {
        this.providerId = providerId;
        this.profileImageUrl = profileImageUrl;
    }

    @SuppressWarnings("unchecked")
    public static KakaoUserInfo from(Map<String, Object> attributes) {

        String providerId = String.valueOf(attributes.get("id"));

        Map<String, Object> kakaoAccount =
                (Map<String, Object>) attributes.get("kakao_account");

        Map<String, Object> profile =
                kakaoAccount != null
                        ? (Map<String, Object>) kakaoAccount.get("profile")
                        : null;

        String profileImageUrl = profile != null
                ? (String) profile.get("profile_image_url")
                : null;

        return new KakaoUserInfo(providerId, profileImageUrl);
    }
}