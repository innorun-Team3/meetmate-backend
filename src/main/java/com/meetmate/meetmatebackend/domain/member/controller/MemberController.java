package com.meetmate.meetmatebackend.domain.member.controller;

import com.meetmate.meetmatebackend.domain.auth.dto.request.AuthUser;
import com.meetmate.meetmatebackend.domain.member.dto.response.MemberGetResponse;
import com.meetmate.meetmatebackend.domain.member.service.MemberService;
import com.meetmate.meetmatebackend.global.common.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/members")
  public ResponseEntity<ApiResponse<List<MemberGetResponse>>> getAll() {
    return ResponseEntity.ok(ApiResponse.success(memberService.getAll()));
  }

  @GetMapping("/members/me")
  public ResponseEntity<ApiResponse<MemberGetResponse>> me(
      @AuthenticationPrincipal AuthUser authUser) {
    return ResponseEntity.ok(ApiResponse.success("조회성공", memberService.getOne(authUser.getId())));
  }
}
