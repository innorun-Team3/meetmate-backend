package com.meetmate.meetmatebackend.domain.participation.controller;

import com.meetmate.meetmatebackend.domain.meeting.entity.Gathering;
import com.meetmate.meetmatebackend.domain.member.entity.Member;
import com.meetmate.meetmatebackend.domain.member.enums.Role;
import com.meetmate.meetmatebackend.domain.participation.entity.Participation;
import com.meetmate.meetmatebackend.domain.participation.entity.ParticipationStatus;
import java.time.LocalDateTime;
import org.springframework.test.util.ReflectionTestUtils;

final class ParticipationControllerRestDocsFixture {

  private ParticipationControllerRestDocsFixture() {}

  static Participation participation(
      Long participationId,
      Long memberId,
      Long gatheringId,
      ParticipationStatus participationStatus) {
    Member owner = new Member("owner@meetmate.com", "password", "owner", Role.ROLE_USER);
    ReflectionTestUtils.setField(owner, "id", 2L);
    Member member = new Member("member@meetmate.com", "password", "member", Role.ROLE_USER);
    ReflectionTestUtils.setField(member, "id", memberId);
    Gathering gathering =
        new Gathering(
            owner,
            "러닝 모임",
            "함께 달릴 사람을 찾습니다.",
            10,
            LocalDateTime.of(2026, 8, 26, 22, 0),
            LocalDateTime.of(2026, 8, 27, 22, 0));
    ReflectionTestUtils.setField(gathering, "id", gatheringId);
    Participation participation = new Participation(member, gathering);
    ReflectionTestUtils.setField(participation, "id", participationId);
    ReflectionTestUtils.setField(participation, "status", participationStatus);
    ReflectionTestUtils.setField(participation, "createdAt", LocalDateTime.of(2026, 8, 25, 22, 0));
    ReflectionTestUtils.setField(participation, "updatedAt", LocalDateTime.of(2026, 8, 25, 22, 0));
    return participation;
  }
}
