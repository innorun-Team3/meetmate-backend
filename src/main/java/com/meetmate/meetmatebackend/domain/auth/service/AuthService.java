package com.meetmate.meetmatebackend.domain.auth.service;

import com.meetmate.meetmatebackend.domain.auth.dto.request.SigninRequest;
import com.meetmate.meetmatebackend.domain.auth.dto.request.SignupRequest;
import com.meetmate.meetmatebackend.domain.member.entity.Member;
import com.meetmate.meetmatebackend.domain.member.enums.Role;
import com.meetmate.meetmatebackend.domain.member.exception.EmailNotFoundException;
import com.meetmate.meetmatebackend.domain.member.exception.PasswordNotMatchException;
import com.meetmate.meetmatebackend.domain.member.repository.MemberRepository;
import com.meetmate.meetmatebackend.global.config.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequest request) {
        String password = request.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        
        Member member = new Member(
                request.getEmail(),
                encodedPassword,
                request.getNickname(),
                Role.of(request.getRole())
        );
        memberRepository.save(member);
    }


    public String signin(
            @Valid SigninRequest request
            ) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EmailNotFoundException()
        );

        String rawPassword = request.getPassword();
        String encodedPassword = member.getPassword();

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

        if(!matches) {
            throw new PasswordNotMatchException();
        }

        return jwtUtil.createToken(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getRole()
        );
    }
}
