package com.meetmate.meetmatebackend.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberDeleteRequest {

    @NotBlank
    private String password;
}
