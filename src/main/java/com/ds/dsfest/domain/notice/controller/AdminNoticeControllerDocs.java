package com.ds.dsfest.domain.notice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ds.dsfest.domain.notice.dto.NoticeCreateReqDto;
import com.ds.dsfest.domain.notice.dto.NoticeDetailResDto;
import com.ds.dsfest.domain.notice.dto.NoticeListItemResDto;
import com.ds.dsfest.domain.notice.dto.NoticeUpdateReqDto;
import com.ds.dsfest.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin Notice", description = "어드민 공지사항 관리 API")
@SecurityRequirement(name = "BearerAuth")
public interface AdminNoticeControllerDocs {

  @Operation(
      summary = "공지 작성",
      description =
          "새 공지사항을 작성합니다. 이미지는 선택 사항입니다.\n\n"
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

  @Operation(summary = "공지 리스트 조회", description = "전체 공지사항을 최신순으로 반환합니다.")
  ResponseEntity<ApiResponse<List<NoticeListItemResDto>>> getNoticeList();

  @Operation(summary = "공지 상세 조회", description = "공지사항 상세 내용을 반환합니다.")
  ResponseEntity<ApiResponse<NoticeDetailResDto>> getNoticeDetail(Long noticeId);

  @Operation(
      summary = "공지 수정",
      description =
          "공지사항을 수정합니다. keepImageUrls에 유지할 기존 이미지 URL을 순서대로 담아 전송하세요. 포함되지 않은 기존 이미지는 S3에서 삭제됩니다.\n\n"
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

  @Operation(summary = "공지 삭제", description = "공지사항과 연관된 S3 이미지를 함께 삭제합니다.")
  ResponseEntity<ApiResponse<Void>> deleteNotice(Long noticeId);

  @Operation(summary = "전체 긴급공지 해제", description = "모든 공지의 긴급 플래그를 해제합니다.")
  ResponseEntity<ApiResponse<Void>> clearAllUrgent();
}
