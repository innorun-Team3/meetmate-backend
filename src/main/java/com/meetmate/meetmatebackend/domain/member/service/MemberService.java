package com.meetmate.meetmatebackend.domain.member.service;

import com.meetmate.meetmatebackend.domain.member.dto.response.MemberGetResponse;
import com.meetmate.meetmatebackend.domain.member.entity.Member;
import com.meetmate.meetmatebackend.domain.member.exception.MemberNotFoundException;
import com.meetmate.meetmatebackend.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional(readOnly = true)
  public List<MemberGetResponse> getAll() {
    List<Member> members = memberRepository.findAll();

    return members.stream()
        .map(
            member ->
                new MemberGetResponse(
                    member.getId(), member.getEmail(), member.getNickname(), member.getRole()))
        .toList();
  }

  @Transactional(readOnly = true)
  public MemberGetResponse getOne(Long id) {
    Member member = memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException());

    return new MemberGetResponse(
        member.getId(), member.getEmail(), member.getNickname(), member.getRole());
  }
}
