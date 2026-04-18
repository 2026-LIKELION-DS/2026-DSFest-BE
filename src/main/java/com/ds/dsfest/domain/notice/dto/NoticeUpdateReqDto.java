package com.ds.dsfest.domain.notice.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;

public record NoticeUpdateReqDto(
    @NotBlank String title,
    @NotNull NoticeCategory category,
    boolean urgent,
    @NotBlank String content,
    List<String> keepImageUrls) {}
