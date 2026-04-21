package com.ds.dsfest.domain.booth.controller;

import java.util.List;

import jakarta.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ds.dsfest.domain.booth.dto.BoothListItemResDto;
import com.ds.dsfest.domain.booth.service.BoothService;
import com.ds.dsfest.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booths")
public class BoothController implements BoothControllerDocs {

  private final BoothService boothService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<BoothListItemResDto>>> getBoothsByDay(
      @RequestParam @Min(1) int day) {
    return ResponseEntity.ok(ApiResponse.onSuccess(boothService.getBoothsByDay(day)));
  }
}
