package com.meetmate.meetmatebackend.domain.participation.exception;

import com.meetmate.meetmatebackend.global.error.ServiceException;
import org.springframework.http.HttpStatus;

public class GatheringNotFoundException extends ServiceException {

  public GatheringNotFoundException() {
    super(HttpStatus.NOT_FOUND, "모임을 찾을 수 없습니다.");
  }
}
