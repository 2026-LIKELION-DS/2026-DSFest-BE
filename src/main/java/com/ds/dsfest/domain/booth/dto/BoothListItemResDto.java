package com.ds.dsfest.domain.booth.dto;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.entity.Booth;
import com.ds.dsfest.domain.booth.entity.BoothImage;
import com.ds.dsfest.domain.booth.entity.BoothOperatingDay;
import com.ds.dsfest.domain.booth.entity.BoothTag;

public record BoothListItemResDto(
    Long id,
    int boothNumber,
    String name,
    Set<BoothType> boothTypes,
    String operatingSubject,
    String thumbnailUrl,
    List<String> tags,
    LocalTime startTime,
    LocalTime endTime) {

  public static BoothListItemResDto from(Booth booth, BoothOperatingDay operatingDay) {
    String thumbnailUrl =
        booth.getImages().stream()
            .min(Comparator.comparingInt(BoothImage::getImageOrder))
            .map(BoothImage::getImageUrl)
            .orElse(null);

    List<String> tags = booth.getTags().stream().map(BoothTag::getTagName).toList();

    Set<BoothType> boothTypes =
        booth.getBoothTypes().isEmpty()
            ? EnumSet.noneOf(BoothType.class)
            : EnumSet.copyOf(booth.getBoothTypes());

    return new BoothListItemResDto(
        booth.getId(),
        booth.getBoothNumber(),
        booth.getName(),
        boothTypes,
        booth.getOperatingSubject(),
        thumbnailUrl,
        tags,
        operatingDay.getStartTime(),
        operatingDay.getEndTime());
  }
}
