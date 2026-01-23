package com.resona.domain.member.service;

import com.resona.domain.member.converter.MemberConverter;
import com.resona.domain.member.dto.MemberResDto;
import com.resona.domain.member.entity.Member;
import com.resona.domain.member.exception.MemberException;
import com.resona.domain.member.repository.MemberRepository;
import com.resona.global.response.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void saveNickname(String token, String nickname) {
        String email = getEmailByAccessToken(token);
        Member member = getMemberByEmail(email);
        member.updateNickname(nickname);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResDto.Profile getMemberProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND));

        return MemberConverter.toProfileResDto(member);
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND));
    }

    private String getEmailByAccessToken(String token) {
        String accessToken = token.split(" ")[1];
//        return jwtUtil.getEmail(accessToken);
        return "";
    }
}
