package com.resona.domain.onboarding.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resona.domain.onboarding.dto.GptMessage;
import com.resona.domain.onboarding.dto.GptRequest;
import com.resona.domain.onboarding.dto.GptResponse;
import com.resona.domain.onboarding.dto.OnboardingResDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GptService {
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Value("${openai.api-key}")
  public String openAiApiKey;

  public OnboardingResDto.Song recommendFromGpt(String category, String scene) {
    String prompt =
        """
            너는 음악 추천 시스템이다.
            사용자가 선택한 감정 카테고리 1개, 상황 1개를 기반으로
            한국 노래 1곡만 추천해라.
            단, 유튜브 api를 통해 검색이 가능한 곡이어야 한다.
            존재하지 않는 곡을 만들면 안 된다.

            조건:
            - 반드시 JSON 형식으로만 응답
            - 다른 설명 문장 절대 포함하지 말 것

            응답 형식:
            {
              "title": "노래 제목",
              "artist": "가수명"
            }

            카테고리:%s, 상황:%s
            """
            .formatted(category, scene);

    GptRequest request = new GptRequest();
    request.setMessages(List.of(GptMessage.builder().role("user").content(prompt).build()));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(openAiApiKey);

    HttpEntity<GptRequest> entity = new HttpEntity<>(request, headers);

    ResponseEntity<GptResponse> response =
        restTemplate.postForEntity(
            "https://api.openai.com/v1/chat/completions", entity, GptResponse.class);

    String json = response.getBody().getChoices().get(0).getMessage().getContent();

    try {
      return objectMapper.readValue(json, OnboardingResDto.Song.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("GPT 응답 파싱 실패", e);
    }
  }
}
