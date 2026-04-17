package com.ds.dsfest.domain.user.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds.dsfest.domain.user.dto.GuestUserResDto;
import com.ds.dsfest.domain.user.service.GuestUserService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/guest")
public class GuestUserController implements GuestUserControllerDocs {

  private final GuestUserService guestUserService;

  @PostMapping
  public ResponseEntity<ApiResponse<GuestUserResDto>> createGuestUser() {
    return ResponseEntity.ok(ApiResponse.onSuccess(guestUserService.createGuestUser()));
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<ApiResponse<GuestUserResDto>> getGuestUser(@PathVariable UUID uuid) {
    return ResponseEntity.ok(ApiResponse.onSuccess(guestUserService.getGuestUser(uuid)));
  }
}
