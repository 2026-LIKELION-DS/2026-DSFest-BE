package com.ds.dsfest.domain.artist.service;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.artist.dto.ArtistListResDto;
import com.ds.dsfest.domain.artist.entity.Artist;
import com.ds.dsfest.domain.artist.entity.CountdownStatus;
import com.ds.dsfest.domain.artist.repository.ArtistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtistService {

  private final ArtistRepository artistRepository;
  private final Clock festivalClock;

  // DAY별 아티스트 조회
  public List<ArtistListResDto> getArtistsByDay(int day) {
    List<Artist> artists = artistRepository.findByFestivalDayOrderByStartTimeAsc(day);

    return artists.stream()
        .map(
            artist ->
                ArtistListResDto.builder()
                    .id(artist.getId())
                    .name(artist.getName())
                    .shortBio(artist.getShortBio())
                    .imageUrl(artist.getImageUrl())
                    .festivalDay(artist.getFestivalDay())
                    .performanceDate(artist.getPerformanceDate())
                    .startTime(artist.getStartTime())
                    .endTime(artist.getEndTime())
                    .instagramUrl(artist.getInstagramUrl())
                    .youtubeUrl(artist.getYoutubeUrl())
                    .playlistUrl(artist.getPlaylistUrl()) // ✅ 추가
                    .countdownStatus(calculateCountdownStatus(artist))
                    .build())
        .toList();
  }

  // 오늘 아티스트 조회
  public List<ArtistListResDto> getArtistsByToday() {
    LocalDate today = LocalDate.now(festivalClock);

    int day;

    if (today.equals(LocalDate.of(2026, 5, 13))) {
      day = 1;
    } else if (today.equals(LocalDate.of(2026, 5, 14))) {
      day = 2;
    } else if (today.equals(LocalDate.of(2026, 5, 15))) {
      day = 3;
    } else {
      day = 1; // 축제 기간이 아니면 기본 Day1
    }

    return getArtistsByDay(day);
  }

  // 공연 상태 계산
  private CountdownStatus calculateCountdownStatus(Artist artist) {
    LocalDateTime now = LocalDateTime.now(festivalClock);

    LocalDateTime start = LocalDateTime.of(artist.getPerformanceDate(), artist.getStartTime());
    LocalDateTime end = LocalDateTime.of(artist.getPerformanceDate(), artist.getEndTime());

    if (!now.isBefore(end)) {
      return CountdownStatus.ENDED;
    }

    if (!now.isBefore(start)) {
      return CountdownStatus.LIVE;
    }

    Duration duration = Duration.between(now, start);

    if (duration.compareTo(Duration.ofHours(72)) <= 0) {
      return CountdownStatus.WITHIN_72H;
    }

    return CountdownStatus.MORE_THAN_72H;
  }
}
