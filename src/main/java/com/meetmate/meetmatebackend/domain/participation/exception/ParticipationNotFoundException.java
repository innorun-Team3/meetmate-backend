package com.meetmate.meetmatebackend.domain.participation.exception;

import com.meetmate.meetmatebackend.global.error.ServiceException;
import org.springframework.http.HttpStatus;

public class ParticipationNotFoundException extends ServiceException {

  public ParticipationNotFoundException() {
    super(HttpStatus.NOT_FOUND, "참가 신청을 찾을 수 없습니다.");
  }
}
