package com.ds.dsfest.domain.photocontest.controller;

import com.ds.dsfest.domain.photocontest.dto.*;
import com.ds.dsfest.domain.photocontest.service.PhotoContestService;
import com.ds.dsfest.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photo-contest")
public class PhotoContestController implements PhotoContestControllerDocs {

    private final PhotoContestService photoContestService;

    /**
     * 사진 콘테스트 이벤트 현황 상태
     */
    @GetMapping("/status")
    @Override
    public ResponseEntity<ApiResponse<PhotoContestStatusResDto>> getContestStatus() {
        PhotoContestStatusResDto result = photoContestService.getContestStatus();
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    /**
     * 사진 콘테스트 출품작 상세 조회
     *
     * @param photoEntryId 상세 조회할 출품작의 고유 ID
     * @return 출품작 상세 정보(제목, 작성자, 설명, 이미지 URL)를 담은 PhotoDetailResDto
     */
    @GetMapping("/{photoEntryId}")
    @Override
    public ResponseEntity<ApiResponse<PhotoDetailResDto>> getPhotoDetail(
        @PathVariable(name = "photoEntryId") Long photoEntryId
    ) {
        PhotoDetailResDto result = photoContestService.getPhotoDetail(photoEntryId);
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    /**
     * 사진 콘테스트 출품작 목록 조회
     * 주제별로 그룹화된 리스트를 반환합니다.
     */
    @GetMapping
    @Override
    public ResponseEntity<ApiResponse<PhotoListResDto>> getPhotoList() {
        PhotoListResDto result = photoContestService.getPhotoList();
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    /**
     * 사진 콘테스트 출품작 투표하기
     */
    @PostMapping("/vote")
    @Override
    public ResponseEntity<ApiResponse<String>> votePhotos(
        @Valid @RequestBody PhotoVoteReqDto reqDto
    ) {
        photoContestService.votePhotos(reqDto);
        return ResponseEntity.ok(ApiResponse.onSuccess("투표가 성공적으로 완료되었습니다."));
    }

    /**
     * 실시간 사진 랭킹 조회 (일반 학생용)
     */
    @GetMapping("/rank")
    @Override
    public ResponseEntity<ApiResponse<Map<String, List<PhotoRankResDto>>>> getStudentRank() {
        Map<String, List<PhotoRankResDto>> result = photoContestService.getVoteResults(false);
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }
}
