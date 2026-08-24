package com.meetmate.meetmatebackend.domain.member.exception;

import com.meetmate.meetmatebackend.global.error.ServiceException;
import org.springframework.http.HttpStatus;

public class MemberNotValidateActiveException extends ServiceException {
    public MemberNotValidateActiveException() {
        super(HttpStatus.BAD_REQUEST, "활성 상태의 회원이 아닙니다.");
    }
}
