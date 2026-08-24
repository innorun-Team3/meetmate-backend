package com.meetmate.meetmatebackend.domain.meeting.service;

import com.meetmate.meetmatebackend.domain.meeting.dto.request.GatheringCreateRequest;
import com.meetmate.meetmatebackend.domain.meeting.dto.request.GatheringUpdateRequest;
import com.meetmate.meetmatebackend.domain.meeting.dto.response.GatheringResponse;
import com.meetmate.meetmatebackend.domain.meeting.entity.Gathering;
import com.meetmate.meetmatebackend.domain.meeting.repository.GatheringRepository;
import com.meetmate.meetmatebackend.domain.member.entity.Member;
import com.meetmate.meetmatebackend.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GatheringService {

  private final GatheringRepository gatheringRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public GatheringResponse create(Long ownerId, GatheringCreateRequest request) {
    Member owner =
        memberRepository
            .findById(ownerId)
            .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

    Gathering gathering =
        new Gathering(
            owner,
            request.getTitle(),
            request.getContent(),
            request.getCapacity(),
            request.getDeadline(),
            request.getMeetingDate());

    Gathering saved = gatheringRepository.save(gathering);
    return new GatheringResponse(saved);
  }

  public List<GatheringResponse> findAll() {
    return gatheringRepository.findAll().stream().map(GatheringResponse::new).toList();
  }

  public GatheringResponse findById(Long id) {
    Gathering gathering =
        gatheringRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("모임을 찾을 수 없습니다."));

    return new GatheringResponse(gathering);
  }

  @Transactional
  public GatheringResponse update(Long id, GatheringUpdateRequest request) {
    Gathering gathering =
        gatheringRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("모임을 찾을 수 없습니다."));

    gathering.update(
        request.getTitle(),
        request.getContent(),
        request.getCapacity(),
        request.getDeadline(),
        request.getMeetingDate());

    return new GatheringResponse(gathering);
  }

  @Transactional
  public void delete(Long id) {
    Gathering gathering =
        gatheringRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("모임을 찾을 수 없습니다."));

    gatheringRepository.delete(gathering);
  }
}
