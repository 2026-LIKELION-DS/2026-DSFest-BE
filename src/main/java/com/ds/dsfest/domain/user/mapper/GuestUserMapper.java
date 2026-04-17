package com.ds.dsfest.domain.user.mapper;

import org.springframework.stereotype.Component;

import com.ds.dsfest.domain.user.dto.GuestUserResDto;
import com.ds.dsfest.domain.user.entity.GuestUser;

@Component
public class GuestUserMapper {

  public GuestUserResDto toResDto(GuestUser guestUser) {
    return GuestUserResDto.from(guestUser);
  }
}
