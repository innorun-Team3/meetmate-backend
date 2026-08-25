package com.meetmate.meetmatebackend.domain.participation.dto.response;

import com.meetmate.meetmatebackend.domain.participation.entity.Participation;
import com.meetmate.meetmatebackend.domain.participation.entity.ParticipationStatus;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ParticipationResponse {

  private final Long id;
  private final Long memberId;
  private final Long gatheringId;
  private final ParticipationStatus status;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public ParticipationResponse(Participation participation) {
    this.id = participation.getId();
    this.memberId = participation.getMember().getId();
    this.gatheringId = participation.getGathering().getId();
    this.status = participation.getStatus();
    this.createdAt = participation.getCreatedAt();
    this.updatedAt = participation.getUpdatedAt();
  }
}
