package com.resona.domain.onboarding.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class YoutubeResponse {

  private List<Item> items;

  @Getter
  @NoArgsConstructor
  public static class Item {
    private Id id;
    private Snippet snippet;
  }

  @Getter
  @NoArgsConstructor
  public static class Id {
    private String videoId;
  }
}
