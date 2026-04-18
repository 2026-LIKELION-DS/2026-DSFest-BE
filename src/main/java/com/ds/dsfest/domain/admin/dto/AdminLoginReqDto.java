package com.ds.dsfest.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminLoginReqDto(@NotBlank String username, @NotBlank String password) {}
