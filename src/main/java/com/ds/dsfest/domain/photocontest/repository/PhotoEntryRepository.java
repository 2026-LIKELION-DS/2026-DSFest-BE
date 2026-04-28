package com.ds.dsfest.domain.photocontest.repository;

import com.ds.dsfest.domain.photocontest.entity.PhotoEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoEntryRepository extends JpaRepository<PhotoEntry, Long> {
    /**
     * 모든 출품작을 ID 순으로 조회합니다.
     */
    List<PhotoEntry> findAllByOrderByIdAsc();
}
