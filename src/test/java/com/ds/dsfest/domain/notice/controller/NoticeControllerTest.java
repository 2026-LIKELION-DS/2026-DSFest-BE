package com.ds.dsfest.domain.notice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;
import com.ds.dsfest.domain.notice.entity.Notice;
import com.ds.dsfest.domain.notice.repository.NoticeRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class NoticeControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private NoticeRepository noticeRepository;

  @Test
  @DisplayName("긴급공지가 있으면 최신 1개의 id와 title을 반환한다")
  void getUrgentNotice_exists_returnsLatest() throws Exception {
    Notice old = noticeRepository.save(Notice.create("오래된 긴급공지", "내용", NoticeCategory.ETC, true));
    Notice latest = noticeRepository.save(Notice.create("최신 긴급공지", "내용", NoticeCategory.ETC, true));

    mockMvc
        .perform(get("/api/notices/urgent"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isSuccess").value(true))
        .andExpect(jsonPath("$.result.id").value(latest.getId()))
        .andExpect(jsonPath("$.result.title").value("최신 긴급공지"));
  }

  @Test
  @DisplayName("긴급공지가 없으면 result가 null이다")
  void getUrgentNotice_none_returnsNullResult() throws Exception {
    noticeRepository.save(Notice.create("일반 공지", "내용", NoticeCategory.ETC, false));

    mockMvc
        .perform(get("/api/notices/urgent"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isSuccess").value(true))
        .andExpect(jsonPath("$.result").doesNotExist());
  }

  @Test
  @DisplayName("키워드로 공지를 검색하면 제목·본문 일치 결과를 반환한다")
  void searchNotices_found_returnsResults() throws Exception {
    noticeRepository.save(
        Notice.create("무대 입장 안내", "1번 게이트로 입장", NoticeCategory.PERFORMANCE, false));
    noticeRepository.save(Notice.create("푸드트럭 안내", "B구역에 위치합니다", NoticeCategory.ETC, false));

    mockMvc
        .perform(get("/api/notices/search").param("keyword", "무대"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.results").isArray())
        .andExpect(jsonPath("$.result.results[0].title").value("무대 입장 안내"))
        .andExpect(jsonPath("$.result.recommended").isEmpty());
  }

  @Test
  @DisplayName("검색 결과가 없으면 results는 비어있고 조회수 상위 공지가 recommended에 담긴다")
  void searchNotices_notFound_returnsRecommended() throws Exception {
    noticeRepository.save(Notice.create("자주 찾는 공지1", "내용1", NoticeCategory.ETC, false));
    noticeRepository.save(Notice.create("자주 찾는 공지2", "내용2", NoticeCategory.ETC, false));

    mockMvc
        .perform(get("/api/notices/search").param("keyword", "존재하지않는키워드xyz"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.results").isEmpty())
        .andExpect(jsonPath("$.result.recommended").isArray());
  }

  @Test
  @DisplayName("본문에 키워드가 있어도 검색 결과에 포함된다")
  void searchNotices_keywordInContent_returnsResult() throws Exception {
    noticeRepository.save(Notice.create("공지 제목", "본문에 게이트 정보가 있습니다", NoticeCategory.ETC, false));

    mockMvc
        .perform(get("/api/notices/search").param("keyword", "게이트"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.results[0].title").value("공지 제목"));
  }
}
