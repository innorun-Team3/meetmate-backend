package com.meetmate.meetmatebackend.domain.participation.controller;

import com.meetmate.meetmatebackend.domain.auth.dto.request.AuthUser;
import com.meetmate.meetmatebackend.domain.participation.dto.response.ParticipationResponse;
import com.meetmate.meetmatebackend.domain.participation.service.ParticipationService;
import com.meetmate.meetmatebackend.global.common.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ParticipationController {

  private final ParticipationService participationService;

  @PostMapping("/gatherings/{gatheringId}/participations")
  public ResponseEntity<ApiResponse<ParticipationResponse>> apply(
      @AuthenticationPrincipal AuthUser authUser, @PathVariable Long gatheringId) {
    return ResponseEntity.ok(
        ApiResponse.success(participationService.apply(authUser, gatheringId)));
  }

  @PatchMapping("/gatherings/{gatheringId}/participations/{participationId}/approve")
  public ResponseEntity<ApiResponse<ParticipationResponse>> approve(
      @AuthenticationPrincipal AuthUser authUser,
      @PathVariable Long gatheringId,
      @PathVariable Long participationId) {
    return ResponseEntity.ok(
        ApiResponse.success(participationService.approve(authUser, gatheringId, participationId)));
  }

  @PatchMapping("/gatherings/{gatheringId}/participations/{participationId}/reject")
  public ResponseEntity<ApiResponse<ParticipationResponse>> reject(
      @AuthenticationPrincipal AuthUser authUser,
      @PathVariable Long gatheringId,
      @PathVariable Long participationId) {
    return ResponseEntity.ok(
        ApiResponse.success(participationService.reject(authUser, gatheringId, participationId)));
  }

  @PatchMapping("/participations/{participationId}/cancel")
  public ResponseEntity<ApiResponse<ParticipationResponse>> cancel(
      @AuthenticationPrincipal AuthUser authUser, @PathVariable Long participationId) {
    return ResponseEntity.ok(
        ApiResponse.success(participationService.cancel(authUser, participationId)));
  }

  @GetMapping("/participations/me")
  public ResponseEntity<ApiResponse<List<ParticipationResponse>>> getMine(
      @AuthenticationPrincipal AuthUser authUser) {
    return ResponseEntity.ok(ApiResponse.success(participationService.getMine(authUser)));
  }

  @GetMapping("/gatherings/{gatheringId}/participations")
  public ResponseEntity<ApiResponse<List<ParticipationResponse>>> getGatheringParticipations(
      @AuthenticationPrincipal AuthUser authUser, @PathVariable Long gatheringId) {
    return ResponseEntity.ok(
        ApiResponse.success(
            participationService.getGatheringParticipations(authUser, gatheringId)));
  }
}
