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
import java.util.Optional;
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

  @Override
  public MemberResDto.ProfileImage getProfileImage(String token) {
    Long memberId = getMemberIdByAccessToken(token);
    Member member = getMemberById(memberId);
    return MemberConverter.toProfileImage(member);
  }

    @Override
    public MemberResDto.Profile getMyProfile(String token) {
        Long memberId = getMemberIdByAccessToken(token);
        Member member =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND));

        return MemberConverter.toProfileResDto(member);
    }

    @Transactional
  public LoginResult loginOrSignUp(KakaoUserInfo userInfo) {
    // 1. DB에 존재하는지 확인
    Optional<Member> optionalMember = memberRepository.findByProviderId(userInfo.getProviderId());

    // 2. 존재하지 않으면 신규 유저
    boolean isNewUser = optionalMember.isEmpty();

    // 3. 존재하면 가져오고, 없으면 저장
    Member member =
        optionalMember.orElseGet(() -> memberRepository.save(Member.createKakao(userInfo)));
    return new LoginResult(member, isNewUser);
  }

  private Long getMemberIdByAccessToken(String token) {
    String accessToken = token.split(" ")[1];
    return jwtProvider.getMemberId(accessToken);
  }
}
