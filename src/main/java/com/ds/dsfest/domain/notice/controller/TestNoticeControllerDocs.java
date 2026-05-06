package com.ds.dsfest.domain.notice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ds.dsfest.domain.notice.dto.NoticeCreateReqDto;
import com.ds.dsfest.domain.notice.dto.NoticeDetailResDto;
import com.ds.dsfest.domain.notice.dto.NoticeUpdateReqDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Test Notice", description = "인증 없이 공지사항 작성·수정 테스트용 API (로그인 불필요)")
public interface TestNoticeControllerDocs {

  @Operation(
      summary = "[TEST] 공지 작성",
      description =
          "로그인 없이 공지사항을 작성합니다. 테스트 API입니다.\n\n"
              + "**category** 가능한 값\n"
              + "- `EVENT` : 이벤트\n"
              + "- `PERFORMANCE` : 공연\n"
              + "- `ETC` : 기타\n"
              + "- `NOTICE` : 안내")
  @RequestBody(
      content =
          @Content(
              mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
              encoding = @Encoding(name = "data", contentType = MediaType.APPLICATION_JSON_VALUE)))
  ResponseEntity<ApiResponse<NoticeDetailResDto>> createNotice(
      NoticeCreateReqDto req, List<MultipartFile> images) throws IOException;

  @Operation(
      summary = "[TEST] 공지 수정",
      description =
          "로그인 없이 공지사항을 수정합니다. 테스트 API입니다.\n\n"
              + "keepImageUrls에 유지할 기존 이미지 URL을 순서대로 담아 전송하세요. 포함되지 않은 기존 이미지는 S3에서 삭제됩니다.\n\n"
              + "**category** 가능한 값\n"
              + "- `EVENT` : 이벤트\n"
              + "- `PERFORMANCE` : 공연\n"
              + "- `ETC` : 기타\n"
              + "- `NOTICE` : 안내")
  @RequestBody(
      content =
          @Content(
              mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
              encoding = @Encoding(name = "data", contentType = MediaType.APPLICATION_JSON_VALUE)))
  ResponseEntity<ApiResponse<NoticeDetailResDto>> updateNotice(
      Long noticeId, NoticeUpdateReqDto req, List<MultipartFile> newImages) throws IOException;

  @Operation(summary = "[TEST] 공지 삭제", description = "로그인 없이 공지사항을 삭제합니다. 테스트 API입니다.")
  ResponseEntity<ApiResponse<Void>> deleteNotice(Long noticeId);
}
