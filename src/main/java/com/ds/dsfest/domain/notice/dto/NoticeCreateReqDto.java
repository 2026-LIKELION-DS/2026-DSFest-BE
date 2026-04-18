package com.ds.dsfest.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;

public record NoticeCreateReqDto(
    @NotBlank @Size(max = 255) String title,
    @NotNull NoticeCategory category,
    boolean urgent,
    @NotBlank String content) {}
