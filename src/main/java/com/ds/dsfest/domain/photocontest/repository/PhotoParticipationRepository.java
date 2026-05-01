package com.ds.dsfest.domain.photocontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.photocontest.entity.PhotoParticipation;

public interface PhotoParticipationRepository extends JpaRepository<PhotoParticipation, Long> {
  boolean existsByVoterKey(String voterKey);
}
