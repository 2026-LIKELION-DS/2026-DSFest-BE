package com.ds.dsfest.domain.booth.dto;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.entity.Booth;
import com.ds.dsfest.domain.booth.entity.BoothImage;
import com.ds.dsfest.domain.booth.entity.BoothMapPosition;
import com.ds.dsfest.domain.booth.entity.BoothTag;

public record BoothMapItemResDto(
    Long boothId,
    int boothNumber,
    String name,
    Set<BoothType> boothTypes,
    String operatingSubject,
    String thumbnailUrl,
    List<String> tags,
    int positionNumber) {

  public static BoothMapItemResDto from(BoothMapPosition position) {
    Booth booth = position.getBooth();

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

    return new BoothMapItemResDto(
        booth.getId(),
        booth.getBoothNumber(),
        booth.getName(),
        boothTypes,
        booth.getOperatingSubject(),
        thumbnailUrl,
        tags,
        position.getPositionNumber());
  }
}
