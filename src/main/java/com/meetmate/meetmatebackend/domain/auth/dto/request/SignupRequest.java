package com.meetmate.meetmatebackend.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequest {

  @Email(message = "이메일 형식이 올바르지 않습니다")
  private String email;

  @NotBlank
  @Min(value = 8, message = "8자이상 입력하시오")
  private String password;

  @NotBlank
  @Max(value = 20, message = "20자 이상은 불가합니다")
  private String nickname;

  @NotBlank private String role;
}
