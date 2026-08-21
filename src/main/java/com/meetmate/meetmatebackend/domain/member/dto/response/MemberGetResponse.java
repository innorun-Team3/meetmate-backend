package com.meetmate.meetmatebackend.domain.member.dto.response;

import com.meetmate.meetmatebackend.domain.member.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberGetResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String role;

    public MemberGetResponse(Long id, String email, String nickname, Role role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.role = role.toString();
    }
}
