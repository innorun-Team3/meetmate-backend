package com.meetmate.meetmatebackend.domain.meeting.dto.response;

import com.meetmate.meetmatebackend.domain.meeting.entity.Gathering;
import com.meetmate.meetmatebackend.domain.meeting.entity.GatheringStatus;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GatheringResponse {

  private final Long id;
  private final Long ownerId;
  private final String title;
  private final String content;
  private final Integer capacity;
  private final LocalDateTime deadline;
  private final LocalDateTime meetingDate;
  private final GatheringStatus status;

  public GatheringResponse(Gathering gathering) {
    this.id = gathering.getId();
    this.ownerId = gathering.getOwner().getId();
    this.title = gathering.getTitle();
    this.content = gathering.getContent();
    this.capacity = gathering.getCapacity();
    this.deadline = gathering.getDeadline();
    this.meetingDate = gathering.getMeetingDate();
    this.status = gathering.getStatus();
  }
}
