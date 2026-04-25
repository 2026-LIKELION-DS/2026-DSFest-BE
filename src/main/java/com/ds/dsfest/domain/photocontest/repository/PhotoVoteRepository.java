package com.ds.dsfest.domain.photocontest.repository;

import com.ds.dsfest.domain.photocontest.entity.PhotoVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhotoVoteRepository extends JpaRepository<PhotoVote, Long> {
    boolean existsByVoterKey(String voterKey);

    /**
     * 사진별 투표 수를 집계하여 결과가 많은 순으로 정렬
     */
    @Query("SELECT v.photoEntry.id, COUNT(v) as voteCount " +
        "FROM PhotoVote v " +
        "GROUP BY v.photoEntry.id " +
        "ORDER BY voteCount DESC")
    List<Object[]> countVotesPerPhoto();
}
