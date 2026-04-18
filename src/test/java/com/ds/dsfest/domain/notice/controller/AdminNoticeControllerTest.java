package com.ds.dsfest.domain.notice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;
import com.ds.dsfest.domain.notice.dto.NoticeCreateReqDto;
import com.ds.dsfest.domain.notice.dto.NoticeUpdateReqDto;
import com.ds.dsfest.domain.notice.entity.Notice;
import com.ds.dsfest.domain.notice.repository.NoticeRepository;
import com.ds.dsfest.global.infra.s3.S3Uploader;
import com.ds.dsfest.global.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AdminNoticeControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private NoticeRepository noticeRepository;
  @Autowired private JwtProvider jwtProvider;
  @MockitoBean private S3Uploader s3Uploader;

  private String authHeader;

  @BeforeEach
  void setUp() {
    authHeader = "Bearer " + jwtProvider.generateToken("test-admin");
  }

  @Test
  @DisplayName("공지를 이미지 없이 작성하면 200을 반환한다")
  void createNotice_withoutImages_success() throws Exception {
    NoticeCreateReqDto req =
        new NoticeCreateReqDto("테스트 공지", NoticeCategory.ETC, false, "공지 내용입니다.");

    MockMultipartFile dataPart =
        new MockMultipartFile(
            "data", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(req));

    mockMvc
        .perform(multipart("/api/admin/notices").file(dataPart).header("Authorization", authHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isSuccess").value(true))
        .andExpect(jsonPath("$.result.title").value("테스트 공지"))
        .andExpect(jsonPath("$.result.category").value("ETC"))
        .andExpect(jsonPath("$.result.urgent").value(false));
  }

  @Test
  @DisplayName("공지를 이미지와 함께 작성하면 S3에 업로드된 URL이 포함된다")
  void createNotice_withImages_success() throws Exception {
    when(s3Uploader.upload(any(), eq("notices")))
        .thenReturn("https://DSFest.s3.ap-northeast-2.amazonaws.com/notices/uuid_test.jpg");

    NoticeCreateReqDto req =
        new NoticeCreateReqDto("이미지 공지", NoticeCategory.EVENT, true, "긴급 공지입니다.");

    MockMultipartFile dataPart =
        new MockMultipartFile(
            "data", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(req));
    MockMultipartFile imagePart =
        new MockMultipartFile(
            "images", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "fake-image".getBytes());

    mockMvc
        .perform(
            multipart("/api/admin/notices")
                .file(dataPart)
                .file(imagePart)
                .header("Authorization", authHeader))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.result.imageUrls[0]")
                .value("https://DSFest.s3.ap-northeast-2.amazonaws.com/notices/uuid_test.jpg"));
  }

  @Test
  @DisplayName("JWT 없이 공지 작성 요청하면 401을 반환한다")
  void createNotice_withoutAuth_returnsUnauthorized() throws Exception {
    NoticeCreateReqDto req = new NoticeCreateReqDto("공지", NoticeCategory.ETC, false, "내용");
    MockMultipartFile dataPart =
        new MockMultipartFile(
            "data", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(req));

    mockMvc
        .perform(multipart("/api/admin/notices").file(dataPart))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @DisplayName("공지 리스트를 최신순으로 조회한다")
  void getNoticeList_success() throws Exception {
    noticeRepository.save(Notice.create("공지1", "내용1", NoticeCategory.ETC, false));
    noticeRepository.save(Notice.create("공지2", "내용2", NoticeCategory.EVENT, true));

    mockMvc
        .perform(get("/api/admin/notices").header("Authorization", authHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isSuccess").value(true))
        .andExpect(jsonPath("$.result").isArray())
        .andExpect(jsonPath("$.result[0].title").value("공지2"));
  }

  @Test
  @DisplayName("공지 상세를 조회한다")
  void getNoticeDetail_success() throws Exception {
    Notice saved =
        noticeRepository.save(Notice.create("상세공지", "상세내용", NoticeCategory.NOTICE, false));

    mockMvc
        .perform(
            get("/api/admin/notices/{noticeId}", saved.getId()).header("Authorization", authHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.id").value(saved.getId()))
        .andExpect(jsonPath("$.result.title").value("상세공지"))
        .andExpect(jsonPath("$.result.content").value("상세내용"));
  }

  @Test
  @DisplayName("존재하지 않는 공지 조회 시 404를 반환한다")
  void getNoticeDetail_notFound_returns404() throws Exception {
    mockMvc
        .perform(get("/api/admin/notices/{noticeId}", 99999L).header("Authorization", authHeader))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value("NOTICE001"));
  }

  @Test
  @DisplayName("공지를 수정하면 변경된 내용이 반환된다")
  void updateNotice_success() throws Exception {
    Notice saved =
        noticeRepository.save(Notice.create("원본 제목", "원본 내용", NoticeCategory.ETC, false));

    NoticeUpdateReqDto req =
        new NoticeUpdateReqDto("수정된 제목", NoticeCategory.EVENT, true, "수정된 내용", List.of());
    MockMultipartFile dataPart =
        new MockMultipartFile(
            "data", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(req));

    mockMvc
        .perform(
            multipart("/api/admin/notices/{noticeId}", saved.getId())
                .file(dataPart)
                .with(
                    r -> {
                      r.setMethod("PUT");
                      return r;
                    })
                .header("Authorization", authHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.title").value("수정된 제목"))
        .andExpect(jsonPath("$.result.urgent").value(true));
  }

  @Test
  @DisplayName("공지를 삭제하면 200과 isSuccess true를 반환한다")
  void deleteNotice_success() throws Exception {
    Notice saved = noticeRepository.save(Notice.create("삭제할 공지", "내용", NoticeCategory.ETC, false));

    mockMvc
        .perform(
            delete("/api/admin/notices/{noticeId}", saved.getId())
                .header("Authorization", authHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isSuccess").value(true));
  }

  @Test
  @DisplayName("전체 긴급공지 해제 시 모든 공지의 urgent가 false가 된다")
  void clearAllUrgent_success() throws Exception {
    noticeRepository.save(Notice.create("긴급1", "내용1", NoticeCategory.ETC, true));
    noticeRepository.save(Notice.create("긴급2", "내용2", NoticeCategory.ETC, true));

    mockMvc
        .perform(patch("/api/admin/notices/urgent/clear").header("Authorization", authHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isSuccess").value(true));

    List<Notice> all = noticeRepository.findAllByOrderByCreatedAtDesc();
    assertThat(all).noneMatch(Notice::isUrgent);
  }
}
