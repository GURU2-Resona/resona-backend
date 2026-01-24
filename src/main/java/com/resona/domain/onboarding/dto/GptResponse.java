package com.resona.domain.onboarding.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class GptResponse {

  private List<Choice> choices;

  @Getter
  public static class Choice {
    private Message message;
  }

  @Getter
  public static class Message {
    private String role;
    private String content;
  }
}
