package com.ds.dsfest.domain.booth.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.entity.Booth;
import com.ds.dsfest.domain.booth.entity.BoothImage;
import com.ds.dsfest.domain.booth.entity.BoothMapPosition;
import com.ds.dsfest.domain.booth.entity.BoothOperatingDay;
import com.ds.dsfest.domain.booth.entity.BoothTag;

public record BoothDetailResDto(
    Long id,
    int boothNumber,
    String name,
    Set<BoothType> boothTypes,
    String operatingSubject,
    String description,
    String openKakaoUrl,
    String everytimeUrl,
    String instagramUrl,
    List<String> tags,
    List<String> imageUrls,
    List<String> operatingDays,
    List<String> positions) {

  private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

  public static BoothDetailResDto from(
      Booth booth,
      List<BoothMapPosition> positionEntities,
      String statusTag,
      LocalDate festivalStartDate) {

    List<String> tags = new ArrayList<>();
    if (statusTag != null) {
      tags.add(statusTag);
    }
    booth.getTags().stream().map(BoothTag::getTagName).forEach(tags::add);

    List<String> imageUrls =
        booth.getImages().stream()
            .sorted(Comparator.comparingInt(BoothImage::getImageOrder))
            .map(BoothImage::getImageUrl)
            .toList();

    List<String> operatingDays = formatOperatingDays(booth.getOperatingDays(), festivalStartDate);
    List<String> positions = formatPositions(positionEntities);

    Set<BoothType> boothTypes =
        booth.getBoothTypes().isEmpty()
            ? EnumSet.noneOf(BoothType.class)
            : EnumSet.copyOf(booth.getBoothTypes());

    return new BoothDetailResDto(
        booth.getId(),
        booth.getBoothNumber(),
        booth.getName(),
        boothTypes,
        booth.getOperatingSubject(),
        booth.getDescription(),
        booth.getOpenKakaoUrl(),
        booth.getEverytimeUrl(),
        booth.getInstagramUrl(),
        tags,
        imageUrls,
        operatingDays,
        positions);
  }

  private static List<String> formatOperatingDays(
      List<BoothOperatingDay> operatingDays, LocalDate festivalStartDate) {
    Map<Integer, List<BoothOperatingDay>> grouped = new LinkedHashMap<>();
    operatingDays.stream()
        .sorted(
            Comparator.comparingInt(BoothOperatingDay::getFestivalDay)
                .thenComparing(BoothOperatingDay::getStartTime))
        .forEach(
            od -> grouped.computeIfAbsent(od.getFestivalDay(), k -> new ArrayList<>()).add(od));

    List<String> result = new ArrayList<>();
    for (Map.Entry<Integer, List<BoothOperatingDay>> entry : grouped.entrySet()) {
      LocalDate date = festivalStartDate.plusDays(entry.getKey() - 1L);
      String header = date.getDayOfMonth() + "일(" + toKoreanDow(date.getDayOfWeek()) + ") ";
      String times =
          entry.getValue().stream()
              .map(
                  od -> od.getStartTime().format(TIME_FMT) + "~" + od.getEndTime().format(TIME_FMT))
              .distinct()
              .reduce((a, b) -> a + ", " + b)
              .orElse("");
      result.add(header + times);
    }
    return result;
  }

  private static List<String> formatPositions(List<BoothMapPosition> positions) {
    return positions.stream()
        .sorted(
            Comparator.comparingInt(BoothMapPosition::getFestivalDay)
                .thenComparing(BoothMapPosition::getDayNightType))
        .map(
            p ->
                p.getFestivalDay()
                    + "일차 "
                    + (p.getDayNightType() == BoothType.DAY ? "낮" : "밤")
                    + ": "
                    + p.getPositionNumber()
                    + "번")
        .toList();
  }

  private static String toKoreanDow(DayOfWeek dow) {
    return switch (dow) {
      case MONDAY -> "월";
      case TUESDAY -> "화";
      case WEDNESDAY -> "수";
      case THURSDAY -> "목";
      case FRIDAY -> "금";
      case SATURDAY -> "토";
      case SUNDAY -> "일";
    };
  }
}
