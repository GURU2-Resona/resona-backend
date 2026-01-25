package com.resona.domain.member.controller;

import com.resona.domain.member.dto.MemberReqDto;
import com.resona.domain.member.dto.MemberResDto;
import com.resona.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface MemberControllerDocs {

  @Operation(summary = "닉네임 저장 API", description = "닉네임을 저장합니다.")
  ApiResponse<Void> saveNickname(
      @RequestHeader("Authorization") String token, @Valid @RequestBody MemberReqDto.Nickname dto);

  @Operation(summary = "카카오 로그인 API", description = "카카오 로그인을 진행합니다.")
  ApiResponse<MemberResDto.Tokens> login(@Valid @RequestBody MemberReqDto.Login dto);

  @Operation(summary = "회원 프로필 조회 API", description = "특정 회원의 프로필 정보(ID, 닉네임, 프로필 이미지)를 조회합니다.")
  ApiResponse<MemberResDto.Profile> getMemberProfile(
      @Parameter(description = "조회할 회원의 ID", example = "1") @PathVariable(name = "memberId")
          Long memberId);

  @Operation(summary = "프로필 사진 조회 API", description = "프로필 사진을 조회합니다.")
  ApiResponse<MemberResDto.ProfileImage> getProfileImage(
      @RequestHeader("Authorization") String token);
}
