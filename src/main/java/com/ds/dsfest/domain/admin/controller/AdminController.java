package com.ds.dsfest.domain.admin.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds.dsfest.domain.admin.dto.AdminLoginReqDto;
import com.ds.dsfest.domain.admin.dto.AdminLoginResDto;
import com.ds.dsfest.domain.admin.service.AdminService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController implements AdminControllerDocs {

  private final AdminService adminService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AdminLoginResDto>> login(
      @RequestBody @Valid AdminLoginReqDto req) {
    return ResponseEntity.ok(ApiResponse.onSuccess(adminService.login(req)));
  }
}
