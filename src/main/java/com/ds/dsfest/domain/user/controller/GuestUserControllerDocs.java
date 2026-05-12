package com.ds.dsfest.domain.user.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.ds.dsfest.domain.user.dto.GuestUserReqDto;
import com.ds.dsfest.domain.user.dto.GuestUserResDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Guest User", description = "게스트 사용자 API")
public interface GuestUserControllerDocs {

  @Operation(
      summary = "게스트 사용자 생성 또는 조회",
      description = "저장된 UUID가 있으면 body에 담아 전송합니다. 유효하면 기존 사용자를, 없거나 만료됐으면 새 UUID를 발급합니다.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "UUID 발급 또는 조회 성공"),
  })
  ResponseEntity<ApiResponse<GuestUserResDto>> createGuestUser(
      @RequestBody(required = false) GuestUserReqDto req);

  @Operation(summary = "게스트 사용자 조회", description = "저장된 UUID의 유효성을 검증합니다.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "조회 성공"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "존재하지 않는 게스트 사용자 (USER_001)"),
  })
  ResponseEntity<ApiResponse<GuestUserResDto>> getGuestUser(
      @Parameter(description = "게스트 UUID") @PathVariable UUID uuid);
}
