package com.resona.domain.member.service;

import com.resona.domain.member.dto.MemberResDto;

public interface MemberService {
  public void saveNickname(String token, String nickname);

  MemberResDto.Profile getMemberProfile(Long memberId);
}
