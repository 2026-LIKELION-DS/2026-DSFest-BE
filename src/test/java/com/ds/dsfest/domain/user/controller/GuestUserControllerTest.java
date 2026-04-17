package com.ds.dsfest.domain.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class GuestUserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  @DisplayName("게스트 사용자 생성 시 UUID를 반환한다")
  void createGuestUser_success() throws Exception {
    mockMvc
        .perform(post("/api/users/guest"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isSuccess").value(true))
        .andExpect(jsonPath("$.code").value("COMMON200"))
        .andExpect(jsonPath("$.result.uuid").isNotEmpty());
  }

  @Test
  @DisplayName("생성된 UUID로 게스트 사용자를 조회할 수 있다")
  void getGuestUser_success() throws Exception {
    MvcResult createResult =
        mockMvc.perform(post("/api/users/guest")).andExpect(status().isOk()).andReturn();

    String uuid =
        objectMapper
            .readTree(createResult.getResponse().getContentAsString())
            .path("result")
            .path("uuid")
            .asText();

    mockMvc
        .perform(get("/api/users/guest/{uuid}", uuid))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isSuccess").value(true))
        .andExpect(jsonPath("$.result.uuid").value(uuid));
  }

  @Test
  @DisplayName("존재하지 않는 UUID 조회 시 404를 반환한다")
  void getGuestUser_notFound() throws Exception {
    mockMvc
        .perform(get("/api/users/guest/{uuid}", "00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.isSuccess").value(false))
        .andExpect(jsonPath("$.code").value("USER_001"));
  }
}
