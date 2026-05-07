package com.ds.dsfest.domain.booth.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.entity.Booth;
import com.ds.dsfest.domain.booth.entity.BoothImage;
import com.ds.dsfest.domain.booth.entity.BoothTag;

public record BoothStatusItemResDto(
    Long id,
    int boothNumber,
    String name,
    Set<BoothType> boothTypes,
    String operatingSubject,
    String thumbnailUrl,
    List<String> tags,
    Integer positionNumber) {

  public static BoothStatusItemResDto from(Booth booth, String statusTag) {
    return from(booth, statusTag, null);
  }

  public static BoothStatusItemResDto from(Booth booth, String statusTag, Integer positionNumber) {
    String thumbnailUrl =
        booth.getImages().stream()
            .min(Comparator.comparingInt(BoothImage::getImageOrder))
            .map(BoothImage::getImageUrl)
            .orElse(null);

    List<String> tags = new ArrayList<>();
    if (statusTag != null) {
      tags.add(statusTag);
    }
    booth.getTags().stream().map(BoothTag::getTagName).forEach(tags::add);

    Set<BoothType> boothTypes =
        booth.getBoothTypes().isEmpty()
            ? EnumSet.noneOf(BoothType.class)
            : EnumSet.copyOf(booth.getBoothTypes());

    return new BoothStatusItemResDto(
        booth.getId(),
        booth.getBoothNumber(),
        booth.getName(),
        boothTypes,
        booth.getOperatingSubject(),
        thumbnailUrl,
        tags,
        positionNumber);
  }

  public BoothStatusItemResDto withPositionNumber(Integer positionNumber) {
    return new BoothStatusItemResDto(
        id, boothNumber, name, boothTypes, operatingSubject, thumbnailUrl, tags, positionNumber);
  }
}
