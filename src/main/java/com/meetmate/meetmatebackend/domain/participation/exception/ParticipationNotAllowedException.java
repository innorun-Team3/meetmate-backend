package com.meetmate.meetmatebackend.domain.participation.exception;

import com.meetmate.meetmatebackend.global.error.ServiceException;
import org.springframework.http.HttpStatus;

public class ParticipationNotAllowedException extends ServiceException {

  public ParticipationNotAllowedException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
