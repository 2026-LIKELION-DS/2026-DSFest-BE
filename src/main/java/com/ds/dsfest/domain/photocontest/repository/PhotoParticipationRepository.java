package com.ds.dsfest.domain.photocontest.repository;

import com.ds.dsfest.domain.photocontest.entity.PhotoParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoParticipationRepository extends JpaRepository<PhotoParticipation, Long> {
    boolean existsByVoterKey(String voterKey);
}
