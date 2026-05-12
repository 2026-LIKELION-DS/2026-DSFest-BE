package com.ds.dsfest.domain.photocontest.dto;

public record PhotoRankResDto(
    Long photoEntryId, String title, String authorName, Long voteCount, String imageUrl) {}
