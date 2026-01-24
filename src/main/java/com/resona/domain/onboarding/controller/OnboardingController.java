package com.resona.domain.onboarding.controller;

import com.resona.domain.onboarding.dto.OnboardingReqDto;
import com.resona.domain.onboarding.dto.OnboardingResDto;
import com.resona.domain.onboarding.service.OnboardingService;
import com.resona.global.response.ApiResponse;
import com.resona.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/onboarding")
@Tag(name = "Onboarding", description = "온보딩 관련 API")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @Operation(summary = "온보딩 노래추천 API", description = "카테고리와 상황을 기반으로 노래를 추천합니다.")
    @PostMapping("/recommend")
    public ApiResponse<OnboardingResDto.Recommend> getRecommendMusic(
            @Valid @RequestBody OnboardingReqDto.Category dto)
    {
        return ApiResponse.onSuccess(SuccessCode.RECOMMEND_OK, onboardingService.recommendMusic(dto.getCategory(), dto.getScene()));
    }
}
