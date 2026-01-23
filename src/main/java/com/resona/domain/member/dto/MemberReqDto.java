package com.resona.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class MemberReqDto {

  @Getter
  public static class Nickname {

    @NotBlank
    @Size(min = 1, max = 20)
    private String nickName;
  }
}
