package com.ds.dsfest.domain.user.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.user.dto.GuestUserResDto;
import com.ds.dsfest.domain.user.entity.GuestUser;
import com.ds.dsfest.domain.user.exception.UserErrorCode;
import com.ds.dsfest.domain.user.mapper.GuestUserMapper;
import com.ds.dsfest.domain.user.repository.GuestUserRepository;
import com.ds.dsfest.global.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestUserService {

  private final GuestUserRepository guestUserRepository;
  private final GuestUserMapper guestUserMapper;

  public GuestUserResDto createGuestUser() {
    GuestUser guestUser = guestUserRepository.save(GuestUser.create());
    return guestUserMapper.toResDto(guestUser);
  }

  @Transactional(readOnly = true)
  public GuestUserResDto getGuestUser(UUID uuid) {
    GuestUser guestUser =
        guestUserRepository
            .findById(uuid)
            .orElseThrow(() -> new CustomException(UserErrorCode.GUEST_NOT_FOUND));
    return guestUserMapper.toResDto(guestUser);
  }
}
