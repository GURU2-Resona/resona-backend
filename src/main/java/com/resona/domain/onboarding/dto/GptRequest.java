package com.resona.domain.onboarding.dto;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GptRequest {
  private String model = "gpt-4o-mini";
  private List<GptMessage> messages;
}
