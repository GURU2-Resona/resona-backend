package com.resona.domain.member.controller;

import com.resona.domain.member.dto.MemberReqDto;
import com.resona.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface MemberControllerDocs {

  @Operation(summary = "닉네임 저장 API", description = "닉네임을 저장합니다.")
  ApiResponse<Void> saveNickname(
      @RequestHeader("Authorization") String token, @Valid @RequestBody MemberReqDto.Nickname dto);
}
