package com.ds.dsfest.domain.artist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.artist.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

  // DAY별 아티스트 조회 (시간순 정렬)
  List<Artist> findByFestivalDayOrderByStartTimeAsc(int festivalDay);
}
