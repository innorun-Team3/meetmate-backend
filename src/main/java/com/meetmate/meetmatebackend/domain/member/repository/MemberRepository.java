package com.meetmate.meetmatebackend.domain.member.repository;

import com.meetmate.meetmatebackend.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
