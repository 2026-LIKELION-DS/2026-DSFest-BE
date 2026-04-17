package com.ds.dsfest.domain.user.dto;

import java.util.UUID;

import com.ds.dsfest.domain.user.entity.GuestUser;

public record GuestUserResDto(UUID uuid) {

  public static GuestUserResDto from(GuestUser guestUser) {
    return new GuestUserResDto(guestUser.getUuid());
  }
}
