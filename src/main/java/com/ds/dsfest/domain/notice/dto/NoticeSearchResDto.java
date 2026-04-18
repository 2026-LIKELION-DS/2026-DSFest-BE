package com.ds.dsfest.domain.notice.dto;

import java.util.List;

public record NoticeSearchResDto(
    List<NoticeListItemResDto> results, List<NoticeListItemResDto> recommended) {}
