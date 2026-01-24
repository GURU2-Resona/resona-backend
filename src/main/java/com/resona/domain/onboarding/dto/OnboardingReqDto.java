package com.resona.domain.onboarding.dto;

import lombok.Getter;

public class OnboardingReqDto {

    @Getter
    public static class Category{
        private String category;
        private String scene;
    }
}
