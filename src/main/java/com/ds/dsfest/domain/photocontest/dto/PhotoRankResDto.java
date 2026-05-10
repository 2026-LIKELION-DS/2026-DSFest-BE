package com.ds.dsfest.domain.photocontest.dto;

public record PhotoRankResDto(
    Long photoEntryId,
    String title,
    String authorName,
    String theme,
    Long voteCount,
    String imageUrl) {}
