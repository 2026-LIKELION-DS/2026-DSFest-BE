package com.ds.dsfest.domain.livetalk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatMessageSendReqDto(
    @NotBlank(message = "guestUuid는 필수입니다.") String guestUuid,
    @NotBlank(message = "메시지는 비어 있을 수 없습니다.") @Size(max = 500, message = "메시지는 500자 이하여야 합니다.")
        String content) {}
