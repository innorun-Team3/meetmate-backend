package com.meetmate.meetmatebackend.domain.member.controller;

import com.meetmate.meetmatebackend.domain.auth.dto.request.AuthUser;
import com.meetmate.meetmatebackend.domain.member.dto.request.MemberDeleteRequest;
import com.meetmate.meetmatebackend.domain.member.dto.request.MemberUpdateRequest;
import com.meetmate.meetmatebackend.domain.member.dto.response.MemberGetResponse;
import com.meetmate.meetmatebackend.domain.member.service.MemberService;
import com.meetmate.meetmatebackend.global.common.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/members/me/password")
  public ResponseEntity<ApiResponse<Void>> updatePassword(
      @AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody MemberUpdateRequest request) {
    memberService.updatePassword(authUser, request);
    return ResponseEntity.ok(ApiResponse.success());
  }

  @DeleteMapping("/members/me")
  public ResponseEntity<ApiResponse<Void>> deleteMe(
      @AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody MemberDeleteRequest request) {
    memberService.deleteMe(authUser, request);
    return ResponseEntity.ok(ApiResponse.success());
  }
}
