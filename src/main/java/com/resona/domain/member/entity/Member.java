package com.resona.domain.member.entity;

import com.resona.domain.member.dto.KakaoUserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "provider_id", nullable = false, unique = true)
  private String providerId;

  @Column(name = "profile_image")
  private String profileImage;

  @Column(name = "nickname")
  private String nickname;

  public void updateNickname(String nickname) {
    this.nickname = nickname;
  }

  public static Member createKakao(KakaoUserInfo userInfo) {
    Member member = new Member();
    member.providerId = userInfo.getProviderId();
    member.profileImage = userInfo.getProfileImageUrl();
    return member;
  }

  public void updateProfileImage(String profileImage){
      this.profileImage = profileImage;
  }
}
