package com.meetmate.meetmatebackend.domain.participation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.meetmate.meetmatebackend.domain.auth.dto.request.AuthUser;
import com.meetmate.meetmatebackend.domain.meeting.entity.Gathering;
import com.meetmate.meetmatebackend.domain.meeting.repository.GatheringRepository;
import com.meetmate.meetmatebackend.domain.member.entity.Member;
import com.meetmate.meetmatebackend.domain.member.enums.MemberStatus;
import com.meetmate.meetmatebackend.domain.member.enums.Role;
import com.meetmate.meetmatebackend.domain.member.repository.MemberRepository;
import com.meetmate.meetmatebackend.domain.participation.dto.response.ParticipationResponse;
import com.meetmate.meetmatebackend.domain.participation.entity.Participation;
import com.meetmate.meetmatebackend.domain.participation.entity.ParticipationStatus;
import com.meetmate.meetmatebackend.domain.participation.exception.ParticipationCapacityExceededException;
import com.meetmate.meetmatebackend.domain.participation.repository.ParticipationRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ParticipationServiceTest {

  @Mock private ParticipationRepository participationRepository;
  @Mock private GatheringRepository gatheringRepository;
  @Mock private MemberRepository memberRepository;

  @InjectMocks private ParticipationService participationService;

  private Member owner;
  private Member applicant;
  private Gathering gathering;

  @BeforeEach
  void setUp() {
    owner = member(1L, "owner@meetmate.com");
    applicant = member(2L, "applicant@meetmate.com");
    gathering =
        new Gathering(
            owner,
            "러닝 모임",
            "함께 달릴 사람을 찾습니다.",
            2,
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(2));
    ReflectionTestUtils.setField(gathering, "id", 10L);
  }

  @Test
  void applyCreatesPendingParticipation() {
    AuthUser authUser = authUser(applicant);
    when(memberRepository.findByIdAndStatusNot(applicant.getId(), MemberStatus.DELETED))
        .thenReturn(Optional.of(applicant));
    when(gatheringRepository.findById(gathering.getId())).thenReturn(Optional.of(gathering));
    when(participationRepository.existsByMemberIdAndGatheringId(
            applicant.getId(), gathering.getId()))
        .thenReturn(false);
    when(participationRepository.save(any(Participation.class)))
        .thenAnswer(
            invocation -> {
              Participation participation = invocation.getArgument(0);
              ReflectionTestUtils.setField(participation, "id", 20L);
              return participation;
            });

    ParticipationResponse response = participationService.apply(authUser, gathering.getId());

    assertEquals(20L, response.getId());
    assertEquals(applicant.getId(), response.getMemberId());
    assertEquals(gathering.getId(), response.getGatheringId());
    assertEquals(ParticipationStatus.PENDING, response.getStatus());
    verify(participationRepository).save(any(Participation.class));
  }

  @Test
  void approveRejectsWhenCapacityIsFull() {
    AuthUser ownerAuthUser = authUser(owner);
    Participation participation = new Participation(applicant, gathering);
    ReflectionTestUtils.setField(participation, "id", 20L);

    when(gatheringRepository.findByIdForUpdate(gathering.getId()))
        .thenReturn(Optional.of(gathering));
    when(participationRepository.findById(participation.getId()))
        .thenReturn(Optional.of(participation));
    when(participationRepository.countByGatheringIdAndStatus(
            gathering.getId(), ParticipationStatus.APPROVED))
        .thenReturn((long) gathering.getCapacity());

    assertThrows(
        ParticipationCapacityExceededException.class,
        () ->
            participationService.approve(ownerAuthUser, gathering.getId(), participation.getId()));
    assertEquals(ParticipationStatus.PENDING, participation.getStatus());
  }

  @Test
  void approveChangesPendingParticipationToApproved() {
    AuthUser ownerAuthUser = authUser(owner);
    Participation participation = new Participation(applicant, gathering);
    ReflectionTestUtils.setField(participation, "id", 20L);

    when(gatheringRepository.findByIdForUpdate(gathering.getId()))
        .thenReturn(Optional.of(gathering));
    when(participationRepository.findById(participation.getId()))
        .thenReturn(Optional.of(participation));
    when(participationRepository.countByGatheringIdAndStatus(
            eq(gathering.getId()), eq(ParticipationStatus.APPROVED)))
        .thenReturn(1L);

    ParticipationResponse response =
        participationService.approve(ownerAuthUser, gathering.getId(), participation.getId());

    assertEquals(ParticipationStatus.APPROVED, response.getStatus());
    assertEquals(ParticipationStatus.APPROVED, participation.getStatus());
  }

  private Member member(Long id, String email) {
    Member member = new Member(email, "encoded-password", email, Role.ROLE_USER);
    ReflectionTestUtils.setField(member, "id", id);
    return member;
  }

  private AuthUser authUser(Member member) {
    return new AuthUser(member.getId(), member.getEmail(), member.getNickname(), member.getRole());
  }
}
