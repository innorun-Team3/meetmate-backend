package com.meetmate.meetmatebackend.domain.member.exception;

import com.meetmate.meetmatebackend.global.error.ServiceException;
import org.springframework.http.HttpStatus;

public class RoleNotMatchException extends ServiceException {

    public RoleNotMatchException() {
        super(HttpStatus.BAD_REQUEST, "해당 Role은 유효하지 않습니다.");
    }
}
