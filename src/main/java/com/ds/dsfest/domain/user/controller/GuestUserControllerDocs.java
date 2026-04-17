package com.ds.dsfest.domain.user.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.ds.dsfest.domain.user.dto.GuestUserResDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Guest User", description = "게스트 사용자 API")
public interface GuestUserControllerDocs {

  @Operation(summary = "게스트 사용자 생성", description = "앱 최초 진입 시 UUID를 발급합니다. 이 UUID를 저장하여 이후 요청에 사용합니다.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "UUID 발급 성공"),
  })
  ResponseEntity<ApiResponse<GuestUserResDto>> createGuestUser();

  @Operation(summary = "게스트 사용자 조회", description = "저장된 UUID의 유효성을 검증합니다.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 게스트 사용자 (USER_001)"),
  })
  ResponseEntity<ApiResponse<GuestUserResDto>> getGuestUser(
      @Parameter(description = "게스트 UUID") @PathVariable UUID uuid);
}
