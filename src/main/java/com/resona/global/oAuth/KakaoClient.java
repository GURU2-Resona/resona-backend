package com.resona.global.oAuth;

import com.resona.domain.member.dto.KakaoUserInfo;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoClient {

  private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

  public KakaoUserInfo getUserInfo(String accessToken) {
    WebClient webClient = WebClient.create();

    Map<String, Object> response =
        webClient
            .get()
            .uri(USER_INFO_URL)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(Map.class)
            .block();

    return KakaoUserInfo.from(response);
  }
}
