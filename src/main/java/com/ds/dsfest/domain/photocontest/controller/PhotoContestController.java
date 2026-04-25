package com.ds.dsfest.domain.photocontest.controller;

import com.ds.dsfest.domain.photocontest.dto.PhotoContestStatusResDto;
import com.ds.dsfest.domain.photocontest.service.PhotoContestService;
import com.ds.dsfest.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photo-contest")
public class PhotoContestController implements PhotoContestControllerDocs {

    private final PhotoContestService photoContestService;

    @GetMapping("/status")
    @Override
    public ResponseEntity<ApiResponse<PhotoContestStatusResDto>> getContestStatus() {
        PhotoContestStatusResDto result = photoContestService.getContestStatus();
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }
}
