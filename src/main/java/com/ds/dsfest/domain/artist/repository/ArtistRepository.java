package com.ds.dsfest.domain.artist.repository;

import com.ds.dsfest.domain.artist.entity.Artist;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

  // DAY별 아티스트 조회 (시간순 정렬)
  List<Artist> findByFestivalDayOrderByStartTimeAsc(int festivalDay);

  // 날짜별(오늘) 아티스트 조회 (시간순 정렬)
  List<Artist> findByPerformanceDateOrderByStartTimeAsc(LocalDate performanceDate);
}
