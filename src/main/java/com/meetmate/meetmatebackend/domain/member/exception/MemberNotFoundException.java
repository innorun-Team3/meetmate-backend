package com.meetmate.meetmatebackend.domain.member.exception;

import com.meetmate.meetmatebackend.global.error.ServiceException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends ServiceException {
    public MemberNotFoundException() {
        super(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다.");
    }
}
