package com.ds.dsfest.domain.admin.controller;

import org.springframework.http.ResponseEntity;

import com.ds.dsfest.domain.admin.dto.AdminLoginReqDto;
import com.ds.dsfest.domain.admin.dto.AdminLoginResDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin Auth", description = "어드민 인증 API")
public interface AdminControllerDocs {

  @Operation(summary = "어드민 로그인", description = "아이디/비밀번호 검증 후 JWT를 발급합니다.")
  ResponseEntity<ApiResponse<AdminLoginResDto>> login(AdminLoginReqDto req);
}
