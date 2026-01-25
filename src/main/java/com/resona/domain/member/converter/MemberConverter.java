package com.resona.domain.member.converter;

import com.resona.domain.member.dto.MemberResDto;
import com.resona.domain.member.entity.Member;

public class MemberConverter {

  public static MemberResDto.Profile toProfileResDto(Member member) {
    return MemberResDto.Profile.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .profileImage(member.getProfileImage())
        .build();
  }

  public static MemberResDto.ProfileImage toProfileImage(Member member){
      return MemberResDto.ProfileImage.builder()
              .profileImage(member.getProfileImage())
              .build();
  }
}
