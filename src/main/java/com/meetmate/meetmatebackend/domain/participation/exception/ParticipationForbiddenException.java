package com.meetmate.meetmatebackend.domain.participation.exception;

import com.meetmate.meetmatebackend.global.error.ServiceException;
import org.springframework.http.HttpStatus;

public class ParticipationForbiddenException extends ServiceException {

  public ParticipationForbiddenException() {
    super(HttpStatus.FORBIDDEN, "참가 신청을 변경할 권한이 없습니다.");
  }
}
