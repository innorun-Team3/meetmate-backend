package com.meetmate.meetmatebackend.domain.member.service;

import com.meetmate.meetmatebackend.domain.auth.dto.request.AuthUser;
import com.meetmate.meetmatebackend.domain.member.dto.request.MemberDeleteRequest;
import com.meetmate.meetmatebackend.domain.member.dto.request.MemberUpdateRequest;
import com.meetmate.meetmatebackend.domain.member.dto.response.MemberGetResponse;
import com.meetmate.meetmatebackend.domain.member.entity.Member;
import com.meetmate.meetmatebackend.domain.member.enums.MemberStatus;
import com.meetmate.meetmatebackend.domain.member.exception.MemberNotFoundException;
import com.meetmate.meetmatebackend.domain.member.exception.PasswordNotMatchException;
import com.meetmate.meetmatebackend.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public List<MemberGetResponse> getAll() {
    List<Member> members = memberRepository.findAllByStatusNot(MemberStatus.DELETED);

    return members.stream()
        .map(
            member ->
                new MemberGetResponse(
                    member.getId(), member.getEmail(), member.getNickname(), member.getRole()))
        .toList();
  }

  @Transactional(readOnly = true)
  public MemberGetResponse getOne(Long id) {
    Member member =
        memberRepository
            .findByIdAndStatusNot(id, MemberStatus.DELETED)
            .orElseThrow(() -> new MemberNotFoundException());

    return new MemberGetResponse(
        member.getId(), member.getEmail(), member.getNickname(), member.getRole());
  }

  @Transactional
  public void updatePassword(AuthUser authUser, MemberUpdateRequest request) {
    Member member =
        memberRepository
            .findById(authUser.getId())
            .orElseThrow(() -> new PasswordNotMatchException());

    member.validateActive();

    String oldEncdoedPassword = member.getPassword();
    String oldRawPassword = request.getOldPassword();

    boolean matches = passwordEncoder.matches(oldRawPassword, oldEncdoedPassword);
    if (!matches) {
      throw new PasswordNotMatchException();
    }

    member.updatePassword(request.getNewPassword());
  }

  @Transactional
  public void deleteMe(AuthUser authUser, MemberDeleteRequest request) {
    Member member =
        memberRepository
            .findById(authUser.getId())
            .orElseThrow(() -> new MemberNotFoundException());
    String rawPassword = request.getPassword();
    String encodedPassword = member.getPassword();
    boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

    if (!matches) {
      throw new PasswordNotMatchException();
    }

    member.delete();
  }
}
