package com.resona.domain.onboarding.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GptMessage {
    private String role;
    private String content;
}