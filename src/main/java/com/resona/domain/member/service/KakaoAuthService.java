package com.resona.domain.member.service;

import com.resona.domain.member.dto.KakaoUserInfo;
import com.resona.domain.member.dto.LoginResult;
import com.resona.domain.member.dto.MemberResDto;
import com.resona.domain.member.entity.Member;
import com.resona.global.oAuth.JwtProvider;
import com.resona.global.oAuth.KakaoClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

  private final MemberServiceImpl memberService;
  private final KakaoClient kakaoClient;
  private final JwtProvider jwtProvider;

  @Transactional
  public MemberResDto.Tokens login(String kakaoAccessToken) {
    // 1. 카카오 유저 정보 조회 (검증)
    KakaoUserInfo kakaoUser = kakaoClient.getUserInfo(kakaoAccessToken);

    // 2. 회원 생성 or 조회
    LoginResult loginResult = memberService.loginOrSignUp(kakaoUser);
    Member member = loginResult.getMember();
    boolean isNewUser = loginResult.isNewUser();

    // 3. 우리 서비스 토큰 발급
    String accessToken = jwtProvider.createAccessToken(member.getId());
    String refreshToken = jwtProvider.createRefreshToken(member.getId());

    return new MemberResDto.Tokens(accessToken, refreshToken, isNewUser);
  }
}
