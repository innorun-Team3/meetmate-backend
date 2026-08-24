package com.meetmate.meetmatebackend.domain.meeting.repository;

import com.meetmate.meetmatebackend.domain.meeting.entity.Gathering;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select gathering from Gathering gathering where gathering.id = :id")
  Optional<Gathering> findByIdForUpdate(@Param("id") Long id);
}
