package com.ds.dsfest.domain.photocontest.repository;

import com.ds.dsfest.domain.photocontest.entity.PhotoEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoEntryRepository extends JpaRepository<PhotoEntry, Long> {
}
