package com.meetmate.meetmatebackend.domain.participation.entity;

import com.meetmate.meetmatebackend.domain.meeting.entity.Gathering;
import com.meetmate.meetmatebackend.domain.member.entity.Member;
import com.meetmate.meetmatebackend.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
    name = "participations",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_participation_member_gathering",
          columnNames = {"member_id", "gathering_id"})
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participation extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "gathering_id", nullable = false)
  private Gathering gathering;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ParticipationStatus status;

  public Participation(Member member, Gathering gathering) {
    this.member = member;
    this.gathering = gathering;
    this.status = ParticipationStatus.PENDING;
  }

  public boolean isPending() {
    return status == ParticipationStatus.PENDING;
  }

  public boolean isApprovable() {
    return status == ParticipationStatus.PENDING;
  }

  public boolean isCancellable() {
    return status == ParticipationStatus.PENDING || status == ParticipationStatus.APPROVED;
  }

  public void approve() {
    this.status = ParticipationStatus.APPROVED;
  }

  public void reject() {
    this.status = ParticipationStatus.REJECTED;
  }

  public void cancel() {
    this.status = ParticipationStatus.CANCELLED;
  }
}
