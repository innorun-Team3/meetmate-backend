package com.meetmate.meetmatebackend.domain.participation.service;

import com.meetmate.meetmatebackend.domain.auth.dto.request.AuthUser;
import com.meetmate.meetmatebackend.domain.meeting.entity.Gathering;
import com.meetmate.meetmatebackend.domain.meeting.entity.GatheringStatus;
import com.meetmate.meetmatebackend.domain.meeting.repository.GatheringRepository;
import com.meetmate.meetmatebackend.domain.member.entity.Member;
import com.meetmate.meetmatebackend.domain.member.enums.MemberStatus;
import com.meetmate.meetmatebackend.domain.member.exception.MemberNotFoundException;
import com.meetmate.meetmatebackend.domain.member.repository.MemberRepository;
import com.meetmate.meetmatebackend.domain.participation.dto.response.ParticipationResponse;
import com.meetmate.meetmatebackend.domain.participation.entity.Participation;
import com.meetmate.meetmatebackend.domain.participation.entity.ParticipationStatus;
import com.meetmate.meetmatebackend.domain.participation.exception.GatheringNotFoundException;
import com.meetmate.meetmatebackend.domain.participation.exception.ParticipationCapacityExceededException;
import com.meetmate.meetmatebackend.domain.participation.exception.ParticipationDuplicateException;
import com.meetmate.meetmatebackend.domain.participation.exception.ParticipationForbiddenException;
import com.meetmate.meetmatebackend.domain.participation.exception.ParticipationNotAllowedException;
import com.meetmate.meetmatebackend.domain.participation.exception.ParticipationNotFoundException;
import com.meetmate.meetmatebackend.domain.participation.repository.ParticipationRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipationService {

  private final ParticipationRepository participationRepository;
  private final GatheringRepository gatheringRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public ParticipationResponse apply(AuthUser authUser, Long gatheringId) {
    Member member = getActiveMember(authUser.getId());
    Gathering gathering = getGathering(gatheringId);

    validateApplicable(member, gathering);

    if (participationRepository.existsByMemberIdAndGatheringId(member.getId(), gatheringId)) {
      throw new ParticipationDuplicateException();
    }

    Participation participation =
        participationRepository.save(new Participation(member, gathering));
    return new ParticipationResponse(participation);
  }

  @Transactional
  public ParticipationResponse approve(AuthUser authUser, Long gatheringId, Long participationId) {
    Gathering gathering =
        gatheringRepository
            .findByIdForUpdate(gatheringId)
            .orElseThrow(GatheringNotFoundException::new);
    Participation participation = getParticipationForGathering(participationId, gatheringId);

    validateOwner(authUser, gathering);
    validateApprovable(gathering, participation);

    long approvedCount =
        participationRepository.countByGatheringIdAndStatus(
            gatheringId, ParticipationStatus.APPROVED);
    if (approvedCount >= gathering.getCapacity()) {
      throw new ParticipationCapacityExceededException();
    }

    participation.approve();
    return new ParticipationResponse(participation);
  }

  @Transactional
  public ParticipationResponse reject(AuthUser authUser, Long gatheringId, Long participationId) {
    Gathering gathering = getGathering(gatheringId);
    Participation participation = getParticipationForGathering(participationId, gatheringId);

    validateOwner(authUser, gathering);
    if (!participation.isPending()) {
      throw new ParticipationNotAllowedException("대기 중인 참가 신청만 거절할 수 있습니다.");
    }

    participation.reject();
    return new ParticipationResponse(participation);
  }

  @Transactional
  public ParticipationResponse cancel(AuthUser authUser, Long participationId) {
    Participation participation =
        participationRepository
            .findById(participationId)
            .orElseThrow(ParticipationNotFoundException::new);

    if (!participation.getMember().getId().equals(authUser.getId())) {
      throw new ParticipationForbiddenException();
    }
    if (!participation.isCancellable()) {
      throw new ParticipationNotAllowedException("대기 또는 승인된 참가 신청만 취소할 수 있습니다.");
    }

    participation.cancel();
    return new ParticipationResponse(participation);
  }

  public List<ParticipationResponse> getMine(AuthUser authUser) {
    return participationRepository.findAllByMemberIdOrderByCreatedAtDesc(authUser.getId()).stream()
        .map(ParticipationResponse::new)
        .toList();
  }

  public List<ParticipationResponse> getGatheringParticipations(
      AuthUser authUser, Long gatheringId) {
    Gathering gathering = getGathering(gatheringId);
    validateOwner(authUser, gathering);

    return participationRepository.findAllByGatheringIdOrderByCreatedAtDesc(gatheringId).stream()
        .map(ParticipationResponse::new)
        .toList();
  }

  private Member getActiveMember(Long memberId) {
    Member member =
        memberRepository
            .findByIdAndStatusNot(memberId, MemberStatus.DELETED)
            .orElseThrow(MemberNotFoundException::new);
    member.validateActive();
    return member;
  }

  private Gathering getGathering(Long gatheringId) {
    return gatheringRepository.findById(gatheringId).orElseThrow(GatheringNotFoundException::new);
  }

  private Participation getParticipationForGathering(Long participationId, Long gatheringId) {
    Participation participation =
        participationRepository
            .findById(participationId)
            .orElseThrow(ParticipationNotFoundException::new);

    if (!participation.getGathering().getId().equals(gatheringId)) {
      throw new ParticipationNotFoundException();
    }
    return participation;
  }

  private void validateApplicable(Member member, Gathering gathering) {
    if (gathering.getOwner().getId().equals(member.getId())) {
      throw new ParticipationNotAllowedException("모임 주최자는 참가 신청할 수 없습니다.");
    }
    if (gathering.getStatus() != GatheringStatus.RECRUITING) {
      throw new ParticipationNotAllowedException("모집 중인 모임에만 참가 신청할 수 있습니다.");
    }
    if (!LocalDateTime.now().isBefore(gathering.getDeadline())) {
      throw new ParticipationNotAllowedException("모집 마감일이 지난 모임에는 참가 신청할 수 없습니다.");
    }
  }

  private void validateOwner(AuthUser authUser, Gathering gathering) {
    if (!gathering.getOwner().getId().equals(authUser.getId())) {
      throw new ParticipationForbiddenException();
    }
  }

  private void validateApprovable(Gathering gathering, Participation participation) {
    if (gathering.getStatus() != GatheringStatus.RECRUITING) {
      throw new ParticipationNotAllowedException("모집 중인 모임의 신청만 승인할 수 있습니다.");
    }
    if (!LocalDateTime.now().isBefore(gathering.getDeadline())) {
      throw new ParticipationNotAllowedException("모집 마감일이 지난 신청은 승인할 수 없습니다.");
    }
    if (!participation.isApprovable()) {
      throw new ParticipationNotAllowedException("대기 중인 참가 신청만 승인할 수 있습니다.");
    }
  }
}
