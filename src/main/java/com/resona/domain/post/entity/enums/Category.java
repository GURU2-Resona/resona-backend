package com.resona.domain.post.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
  ROMANCE("사랑/설렘"),
  FAREWELL("이별/슬픔"),
  COMFORT("위로/응원"),
  ENERGY("신남/에너지"),
  LONGING("그리움/추억"),
  RELAX("일상/여유"),
  OTHER("기타");

  private final String description;
}
