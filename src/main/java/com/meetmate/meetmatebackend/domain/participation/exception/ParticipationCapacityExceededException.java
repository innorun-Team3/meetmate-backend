package com.meetmate.meetmatebackend.domain.participation.exception;

import com.meetmate.meetmatebackend.global.error.ServiceException;
import org.springframework.http.HttpStatus;

public class ParticipationCapacityExceededException extends ServiceException {

  public ParticipationCapacityExceededException() {
    super(HttpStatus.CONFLICT, "모임의 참가 정원이 찼습니다.");
  }
}
