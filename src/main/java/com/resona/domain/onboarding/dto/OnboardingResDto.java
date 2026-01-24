package com.resona.domain.onboarding.dto;

import lombok.Builder;
import lombok.Getter;

public class OnboardingResDto {

    @Getter
    public static class Song{
        private String title;
        private String artist;
    }

    @Getter
    @Builder
    public static class Recommend{
        private String title;
        private String artist;
        private String youtubeUrl;
    }
}
