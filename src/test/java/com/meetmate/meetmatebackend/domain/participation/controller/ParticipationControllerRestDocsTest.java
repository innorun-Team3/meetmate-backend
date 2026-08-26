package com.meetmate.meetmatebackend.domain.participation.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.meetmate.meetmatebackend.domain.auth.dto.request.AuthUser;
import com.meetmate.meetmatebackend.domain.member.enums.Role;
import com.meetmate.meetmatebackend.domain.participation.dto.response.ParticipationResponse;
import com.meetmate.meetmatebackend.domain.participation.entity.ParticipationStatus;
import com.meetmate.meetmatebackend.domain.participation.service.ParticipationService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class ParticipationControllerRestDocsTest {

  @Mock private ParticipationService participationService;

  @InjectMocks private ParticipationController participationController;

  private MockMvc mockMvc;
  private AuthUser authUser;

  @BeforeEach
  void setUp(RestDocumentationContextProvider restDocumentation) {
    authUser = new AuthUser(1L, "member@meetmate.com", "meetmate", Role.ROLE_USER);
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(authUser, null));
    SecurityContextHolder.setContext(securityContext);

    mockMvc =
        MockMvcBuilders.standaloneSetup(participationController)
            .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
            .apply(documentationConfiguration(restDocumentation))
            .build();
  }

  @AfterEach
  void tearDown() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void getMyParticipations() throws Exception {
    ParticipationResponse response = participationResponse();
    when(participationService.getMine(authUser)).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/participations/me"))
        .andExpect(status().isOk())
        .andDo(
            document(
                "participations-get-mine",
                responseFields(
                    fieldWithPath("success").description("요청 성공 여부"),
                    fieldWithPath("code").description("응답 코드"),
                    fieldWithPath("message").description("응답 메시지"),
                    fieldWithPath("content[].id").description("참가 신청 ID"),
                    fieldWithPath("content[].memberId").description("신청한 회원 ID"),
                    fieldWithPath("content[].gatheringId").description("모임 ID"),
                    fieldWithPath("content[].status").description("참가 신청 상태"),
                    fieldWithPath("content[].createdAt").description("신청 일시"),
                    fieldWithPath("content[].updatedAt").description("마지막 상태 변경 일시"))));
  }

  private ParticipationResponse participationResponse() {
    return new ParticipationResponse(
        ParticipationControllerRestDocsFixture.participation(
            100L, 1L, 10L, ParticipationStatus.PENDING));
  }
}
