package com.meetmate.meetmatebackend.domain.member.exception;

import com.meetmate.meetmatebackend.global.error.ServiceException;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends ServiceException {
  public PasswordNotMatchException() {
    super(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다.");
  }
}
