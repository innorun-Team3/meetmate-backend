package com.meetmate.meetmatebackend.domain.participation.repository;

import com.meetmate.meetmatebackend.domain.participation.entity.Participation;
import com.meetmate.meetmatebackend.domain.participation.entity.ParticipationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

  boolean existsByMemberIdAndGatheringId(Long memberId, Long gatheringId);

  long countByGatheringIdAndStatus(Long gatheringId, ParticipationStatus status);

  List<Participation> findAllByMemberIdOrderByCreatedAtDesc(Long memberId);

  List<Participation> findAllByGatheringIdOrderByCreatedAtDesc(Long gatheringId);
}
