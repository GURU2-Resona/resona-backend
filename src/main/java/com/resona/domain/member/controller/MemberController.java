package com.resona.domain.member.controller;

import com.resona.domain.member.dto.MemberReqDto;
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

  @PatchMapping("nickname")
  public ApiResponse<Void> saveNickname(
      @RequestHeader("Authorization") String token, @Valid @RequestBody MemberReqDto.Nickname dto) {
    memberService.saveNickname(token, dto.getNickName());
    return ApiResponse.onSuccess(SuccessCode.NICKNAME_SAVE_OK, null);
  }
}
