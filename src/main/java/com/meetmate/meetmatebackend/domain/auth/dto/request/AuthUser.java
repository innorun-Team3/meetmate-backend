package com.meetmate.meetmatebackend.domain.auth.dto.request;

import com.meetmate.meetmatebackend.domain.member.enums.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthUser {

    private final Long id;
    private final String email;
    private final String nickname;
    private final Role role;
}
