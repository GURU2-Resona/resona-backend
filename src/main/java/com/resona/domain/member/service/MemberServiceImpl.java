package com.resona.domain.member.service;

import com.resona.domain.member.dto.KakaoUserInfo;
import com.resona.domain.member.entity.Member;
import com.resona.domain.member.exception.MemberException;
import com.resona.domain.member.repository.MemberRepository;
import com.resona.global.oAuth.JwtProvider;
import com.resona.global.response.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

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
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND));
    }

    @Transactional
    public Member loginOrSignUp(KakaoUserInfo userInfo) {
        return memberRepository
                .findByProviderId(userInfo.getProviderId())
                .orElseGet(() -> memberRepository.save(
                        Member.createKakao(userInfo)
                ));
    }


    private Long getMemberIdByAccessToken(String token) {
        String accessToken = token.split(" ")[1];
        return jwtProvider.getMemberId(accessToken);
    }
}
