package com.resona.domain.member.service;

import com.resona.domain.member.converter.MemberConverter;
import com.resona.domain.member.dto.KakaoUserInfo;
import com.resona.domain.member.dto.LoginResult;
import com.resona.domain.member.dto.MemberResDto;
import com.resona.domain.member.entity.Member;
import com.resona.domain.member.exception.MemberException;
import com.resona.domain.member.repository.MemberRepository;
import com.resona.global.oAuth.JwtProvider;
import com.resona.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final JwtProvider jwtProvider;

  @Override
  @Transactional
  public void saveNickname(String token, String nickname) {
    Long memberId = getMemberIdByAccessToken(token);
    Member member = getMemberById(memberId);
    member.updateNickname(nickname);
  }

  private Member getMemberById(Long id) {
    return memberRepository
        .findById(id)
        .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND));
  }

  @Override
  @Transactional(readOnly = true)
  public MemberResDto.Profile getMemberProfile(Long memberId) {
    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND));

    return MemberConverter.toProfileResDto(member);
  }

  @Transactional
  public LoginResult loginOrSignUp(KakaoUserInfo userInfo) {
    Member member =
        memberRepository
            .findByProviderId(userInfo.getProviderId())
            .orElseGet(() -> memberRepository.save(Member.createKakao(userInfo)));

    boolean isNewUser = member.getNickname() == null;
    return new LoginResult(member, isNewUser);
  }

  private Long getMemberIdByAccessToken(String token) {
    String accessToken = token.split(" ")[1];
    return jwtProvider.getMemberId(accessToken);
  }
}
