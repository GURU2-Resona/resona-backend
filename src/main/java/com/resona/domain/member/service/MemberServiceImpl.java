package com.resona.domain.member.service;

import com.resona.domain.member.entity.Member;
import com.resona.domain.member.exception.MemberException;
import com.resona.domain.member.repository.MemberRepository;
import com.resona.global.response.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;

  @Override
  @Transactional
  public void saveNickname(String token, String nickname) {
    String email = getEmailByAccessToken(token);
    Member member = getMemberByEmail(email);
    member.updateNickname(nickname);
  }

  private Member getMemberByEmail(String email) {
    return memberRepository
        .findByEmail(email)
        .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND));
  }

  private String getEmailByAccessToken(String token) {
    String accessToken = token.split(" ")[1];
    //        return jwtUtil.getEmail(accessToken);
    return "";
  }
}
