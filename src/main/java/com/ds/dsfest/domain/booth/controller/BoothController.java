package com.ds.dsfest.domain.booth.controller;

import java.util.List;

import jakarta.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.dto.BoothDetailResDto;
import com.ds.dsfest.domain.booth.dto.BoothListItemResDto;
import com.ds.dsfest.domain.booth.dto.BoothMapItemResDto;
import com.ds.dsfest.domain.booth.dto.BoothStatusItemResDto;
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

  @GetMapping("/by-time")
  public ResponseEntity<ApiResponse<List<BoothListItemResDto>>> getBoothsByDayAndType(
      @RequestParam @Min(1) int day, @RequestParam BoothType type) {
    return ResponseEntity.ok(ApiResponse.onSuccess(boothService.getBoothsByDayAndType(day, type)));
  }

  @GetMapping("/map")
  public ResponseEntity<ApiResponse<List<BoothMapItemResDto>>> getBoothMap(
      @RequestParam @Min(1) int day, @RequestParam BoothType type) {
    return ResponseEntity.ok(ApiResponse.onSuccess(boothService.getBoothMap(day, type)));
  }

  @GetMapping("/{boothId}")
  public ResponseEntity<ApiResponse<BoothDetailResDto>> getBoothDetail(@PathVariable Long boothId) {
    return ResponseEntity.ok(ApiResponse.onSuccess(boothService.getBoothDetail(boothId)));
  }

  @GetMapping("/status")
  public ResponseEntity<ApiResponse<List<BoothStatusItemResDto>>> getBoothsWithStatus(
      @RequestParam @Min(1) int day,
      @RequestParam(required = false, defaultValue = "false") boolean active) {
    return ResponseEntity.ok(ApiResponse.onSuccess(boothService.getBoothsWithStatus(day, active)));
  }

  @GetMapping("/random")
  public ResponseEntity<ApiResponse<BoothStatusItemResDto>> getRandomRecommendedBooth(
      @RequestParam @Min(1) int day) {
    return ResponseEntity.ok(ApiResponse.onSuccess(boothService.getRandomRecommendedBooth(day)));
  }
}
