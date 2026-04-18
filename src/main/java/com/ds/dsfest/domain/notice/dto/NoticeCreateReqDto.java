package com.ds.dsfest.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;

public record NoticeCreateReqDto(
    @NotBlank String title,
    @NotNull NoticeCategory category,
    boolean urgent,
    @NotBlank String content) {}
