package com.resona.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResDto {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(description = "회원 프로필 조회 응답 DTO")
  public static class Profile {
    @Schema(description = "회원 고유 ID", example = "1")
    private Long id;

    @Schema(description = "회원 닉네임", example = "레조나")
    private String nickname;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.png")
    private String profileImage;
  }

  @Getter
  @AllArgsConstructor
  public static class Tokens {
    private String accessToken;
    private String refreshToken;
    private Boolean isNewUser;
    private Date expireAt;
  }

  @Getter
  @Builder
  public static class ProfileImage {
    private String profileImage;
  }
}
