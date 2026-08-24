package com.meetmate.meetmatebackend.domain.member.entity;

import com.meetmate.meetmatebackend.domain.auth.dto.request.AuthUser;
import com.meetmate.meetmatebackend.domain.member.enums.MemberStatus;
import com.meetmate.meetmatebackend.domain.member.enums.Role;
import com.meetmate.meetmatebackend.domain.member.exception.MemberNotValidateActiveException;
import com.meetmate.meetmatebackend.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MemberStatus status;

  public Member(String email, String password, String nickname, Role role) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.role = role;
    this.status = MemberStatus.ACTIVE;
  }

  private Member(Long id) {
    this.id = id;
  }

  public static Member fromAuthUser(AuthUser authUser) {
    return new Member(authUser.getId());
  }

  public void updatePassword(String password) {
    this.password = password;
  }

  //활성화된 회원인지 확인하는 메서드
  public void validateActive() {
    if (this.status != MemberStatus.ACTIVE) {
      throw new MemberNotValidateActiveException();
    }
  }

  // 회원 상태 변경 메서드
  public void suspend() {
    this.status = MemberStatus.SUSPENDED;
  }

  public void activate() {
    this.status = MemberStatus.ACTIVE;
  }

  public void delete() {
    this.status = MemberStatus.DELETED;
  }
}
