package com.meetmate.meetmatebackend.domain.member.repository;

import com.meetmate.meetmatebackend.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

import com.meetmate.meetmatebackend.domain.member.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByEmail(String email);

  Optional<Member> findByIdAndStatusNot(Long id, MemberStatus status);

  List<Member> findAllByStatusNot(MemberStatus status);
}
