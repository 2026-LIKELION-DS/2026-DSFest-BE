package com.ds.dsfest.domain.admin.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.admin.AdminProperties;
import com.ds.dsfest.domain.admin.dto.AdminLoginReqDto;
import com.ds.dsfest.domain.admin.dto.AdminLoginResDto;
import com.ds.dsfest.domain.admin.exception.AdminErrorCode;
import com.ds.dsfest.global.exception.CustomException;
import com.ds.dsfest.global.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

  private final AdminProperties adminProperties;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  public AdminLoginResDto login(AdminLoginReqDto req) {
    if (!adminProperties.getUsername().equals(req.username())
        || !passwordEncoder.matches(req.password(), adminProperties.getPassword())) {
      throw new CustomException(AdminErrorCode.ADMIN_LOGIN_FAILED);
    }
    return new AdminLoginResDto(jwtProvider.generateToken(req.username()));
  }
}
