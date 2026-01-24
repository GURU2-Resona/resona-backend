package com.resona.domain.member.controller;

import com.resona.domain.member.dto.MemberReqDto;
import com.resona.domain.member.dto.MemberResDto;
import com.resona.domain.member.service.KakaoAuthService;
import com.resona.domain.member.service.MemberServiceImpl;
import com.resona.global.response.ApiResponse;
import com.resona.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "Member", description = "회원 관련 API")
public class MemberController implements MemberControllerDocs {

  private final MemberServiceImpl memberService;
  private final KakaoAuthService kakaoAuthService;

  @PatchMapping("/nickname")
  public ApiResponse<Void> saveNickname(
      @RequestHeader("Authorization") String token, @Valid @RequestBody MemberReqDto.Nickname dto) {
    memberService.saveNickname(token, dto.getNickName());
    return ApiResponse.onSuccess(SuccessCode.NICKNAME_SAVE_OK, null);
  }

  @PostMapping("/login/kakao")
  public ApiResponse<MemberResDto.Tokens> login(@Valid @RequestBody MemberReqDto.Login dto) {
    return ApiResponse.onSuccess(SuccessCode.USER_LOGIN_OK, kakaoAuthService.login(dto.getToken()));
  }

  @GetMapping("/{memberId}")
  public ApiResponse<MemberResDto.Profile> getMemberProfile(
      @PathVariable(name = "memberId") Long memberId) {
    MemberResDto.Profile response = memberService.getMemberProfile(memberId);
    return ApiResponse.onSuccess(SuccessCode.MEMBER_PROFILE_GET_OK, response);
  }
}
