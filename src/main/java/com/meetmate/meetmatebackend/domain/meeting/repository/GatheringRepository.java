package com.meetmate.meetmatebackend.domain.meeting.repository;

import com.meetmate.meetmatebackend.domain.meeting.entity.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {}
