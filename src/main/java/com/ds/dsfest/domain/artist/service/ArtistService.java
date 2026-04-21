package com.ds.dsfest.domain.artist.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.artist.dto.ArtistListResponseDto;
import com.ds.dsfest.domain.artist.dto.ArtistPlaylistResponseDto;
import com.ds.dsfest.domain.artist.entity.Artist;
import com.ds.dsfest.domain.artist.entity.CountdownStatus;
import com.ds.dsfest.domain.artist.exception.ArtistErrorCode;
import com.ds.dsfest.domain.artist.repository.ArtistRepository;
import com.ds.dsfest.global.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtistService {

  private final ArtistRepository artistRepository;

  public List<ArtistListResponseDto> getArtistsByDay(int day) {
    List<Artist> artists = artistRepository.findByFestivalDayOrderByStartTimeAsc(day);

    return artists.stream()
        .map(
            artist ->
                ArtistListResponseDto.builder()
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
                    .countdownStatus(calculateCountdownStatus(artist))
                    .build())
        .toList();
  }

  public ArtistPlaylistResponseDto getArtistPlaylist(Long artistId) {
    Artist artist = getArtistById(artistId);

    return ArtistPlaylistResponseDto.builder()
        .artistId(artist.getId())
        .artistName(artist.getName())
        .youtubeUrl(artist.getYoutubeUrl())
        .build();
  }

  private Artist getArtistById(Long artistId) {
    return artistRepository
        .findById(artistId)
        .orElseThrow(() -> new CustomException(ArtistErrorCode.ARTIST_NOT_FOUND));
  }

  private CountdownStatus calculateCountdownStatus(Artist artist) {
    LocalDateTime now = LocalDateTime.now();

    LocalDateTime start = LocalDateTime.of(artist.getPerformanceDate(), artist.getStartTime());

    LocalDateTime end = LocalDateTime.of(artist.getPerformanceDate(), artist.getEndTime());

      if (!now.isBefore(end)) { // now >= end
      return CountdownStatus.ENDED;
    }

      if (!now.isBefore(start)) { // start <= now < end
      return CountdownStatus.LIVE;
    }

      Duration duration = Duration.between(now, start); // now < start 보장됨

      if (duration.compareTo(Duration.ofHours(24)) <= 0) {
      return CountdownStatus.WITHIN_24H;
    }

    return CountdownStatus.MORE_THAN_24H;
  }
}
