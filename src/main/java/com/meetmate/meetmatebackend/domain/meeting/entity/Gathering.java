package com.meetmate.meetmatebackend.domain.meeting.entity;

import com.meetmate.meetmatebackend.domain.member.entity.Member;
import com.meetmate.meetmatebackend.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "gatherings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gathering extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private Member owner;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(nullable = false)
  private Integer capacity;

  @Column(nullable = false)
  private LocalDateTime deadline;

  @Column(nullable = false)
  private LocalDateTime meetingDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private GatheringStatus status;

  public Gathering(
      Member owner,
      String title,
      String content,
      Integer capacity,
      LocalDateTime deadline,
      LocalDateTime meetingDate) {
    this.owner = owner;
    this.title = title;
    this.content = content;
    this.capacity = capacity;
    this.deadline = deadline;
    this.meetingDate = meetingDate;
    this.status = GatheringStatus.RECRUITING;
  }

  public void update(
      String title,
      String content,
      Integer capacity,
      LocalDateTime deadline,
      LocalDateTime meetingDate) {
    this.title = title;
    this.content = content;
    this.capacity = capacity;
    this.deadline = deadline;
    this.meetingDate = meetingDate;
  }

  public void close() {
    this.status = GatheringStatus.CLOSED;
  }
}
