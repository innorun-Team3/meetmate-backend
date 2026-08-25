package com.meetmate.meetmatebackend.domain.participation.exception;

import com.meetmate.meetmatebackend.global.error.ServiceException;
import org.springframework.http.HttpStatus;

public class ParticipationDuplicateException extends ServiceException {

  public ParticipationDuplicateException() {
    super(HttpStatus.CONFLICT, "이미 이 모임에 참가 신청한 회원입니다.");
  }
}
