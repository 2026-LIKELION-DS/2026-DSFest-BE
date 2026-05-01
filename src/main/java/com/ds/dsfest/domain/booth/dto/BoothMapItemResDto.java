package com.ds.dsfest.domain.booth.dto;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.entity.Booth;
import com.ds.dsfest.domain.booth.entity.BoothImage;
import com.ds.dsfest.domain.booth.entity.BoothMapPosition;

public record BoothMapItemResDto(
    Long boothId,
    int boothNumber,
    String name,
    Set<BoothType> boothTypes,
    String operatingSubject,
    String thumbnailUrl,
    int positionNumber) {

  public static BoothMapItemResDto from(BoothMapPosition position) {
    Booth booth = position.getBooth();

    String thumbnailUrl =
        booth.getImages().stream()
            .min(Comparator.comparingInt(BoothImage::getImageOrder))
            .map(BoothImage::getImageUrl)
            .orElse(null);

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
        position.getPositionNumber());
  }
}
