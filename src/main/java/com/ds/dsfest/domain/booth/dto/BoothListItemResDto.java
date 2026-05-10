package com.ds.dsfest.domain.booth.dto;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.entity.Booth;
import com.ds.dsfest.domain.booth.entity.BoothImage;
import com.ds.dsfest.domain.booth.entity.BoothOperatingDay;

public record BoothListItemResDto(
    Long id,
    int boothNumber,
    String name,
    Set<BoothType> boothTypes,
    String operatingSubject,
    String thumbnailUrl,
    List<String> operatingTimes) {

  private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

  public static BoothListItemResDto from(Booth booth, List<BoothOperatingDay> operatingDays) {
    String thumbnailUrl =
        booth.getImages().stream()
            .min(Comparator.comparingInt(BoothImage::getImageOrder))
            .map(BoothImage::getImageUrl)
            .orElse(null);

    Set<BoothType> boothTypes =
        booth.getBoothTypes().isEmpty()
            ? EnumSet.noneOf(BoothType.class)
            : EnumSet.copyOf(booth.getBoothTypes());

    List<String> operatingTimes =
        operatingDays.stream()
            .sorted(Comparator.comparing(BoothOperatingDay::getStartTime))
            .map(od -> od.getStartTime().format(TIME_FMT) + "~" + od.getEndTime().format(TIME_FMT))
            .distinct()
            .toList();

    return new BoothListItemResDto(
        booth.getId(),
        booth.getBoothNumber(),
        booth.getName(),
        boothTypes,
        booth.getOperatingSubject(),
        thumbnailUrl,
        operatingTimes);
  }
}
