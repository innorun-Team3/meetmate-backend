package com.meetmate.meetmatebackend.domain.meeting.controller;

import com.meetmate.meetmatebackend.domain.meeting.dto.request.GatheringCreateRequest;
import com.meetmate.meetmatebackend.domain.meeting.dto.request.GatheringUpdateRequest;
import com.meetmate.meetmatebackend.domain.meeting.dto.response.GatheringResponse;
import com.meetmate.meetmatebackend.domain.meeting.service.GatheringService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gatherings")
public class GatheringController {

  private final GatheringService gatheringService;

  // 모임 생성
  @PostMapping
  public ResponseEntity<GatheringResponse> create(
      @RequestParam Long ownerId, @RequestBody GatheringCreateRequest request) {
    return ResponseEntity.ok(gatheringService.create(ownerId, request));
  }

  // 모임 목록 조회
  @GetMapping
  public ResponseEntity<List<GatheringResponse>> findAll() {
    return ResponseEntity.ok(gatheringService.findAll());
  }

  // 모임 상세 조회
  @GetMapping("/{id}")
  public ResponseEntity<GatheringResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(gatheringService.findById(id));
  }

  // 모임 수정
  @PatchMapping("/{id}")
  public ResponseEntity<GatheringResponse> update(
      @PathVariable Long id, @RequestBody GatheringUpdateRequest request) {
    return ResponseEntity.ok(gatheringService.update(id, request));
  }

  // 모임 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    gatheringService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
