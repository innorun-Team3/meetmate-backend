package com.meetmate.meetmatebackend.domain.meeting.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GatheringCreateRequest {

  private String title;
  private String content;
  private Integer capacity;
  private LocalDateTime deadline;
  private LocalDateTime meetingDate;
}
