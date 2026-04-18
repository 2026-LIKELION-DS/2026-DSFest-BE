package com.ds.dsfest.domain.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AdminControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  @DisplayName("올바른 자격증명으로 로그인하면 accessToken을 반환한다")
  void login_success() throws Exception {
    mockMvc
        .perform(
            post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        Map.of(
                            "username", "test-admin",
                            "password", "test-password"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isSuccess").value(true))
        .andExpect(jsonPath("$.result.accessToken").isNotEmpty());
  }

  @Test
  @DisplayName("비밀번호가 틀리면 401을 반환한다")
  void login_wrongPassword_returnsUnauthorized() throws Exception {
    mockMvc
        .perform(
            post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        Map.of(
                            "username", "test-admin",
                            "password", "wrong-password"))))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.isSuccess").value(false))
        .andExpect(jsonPath("$.code").value("ADMIN001"));
  }

  @Test
  @DisplayName("아이디가 틀리면 401을 반환한다")
  void login_wrongUsername_returnsUnauthorized() throws Exception {
    mockMvc
        .perform(
            post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        Map.of(
                            "username", "wrong-admin",
                            "password", "test-password"))))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.isSuccess").value(false))
        .andExpect(jsonPath("$.code").value("ADMIN001"));
  }

  @Test
  @DisplayName("username이 빈 값이면 400을 반환한다")
  void login_blankUsername_returnsBadRequest() throws Exception {
    mockMvc
        .perform(
            post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        Map.of(
                            "username", "",
                            "password", "test-password"))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.isSuccess").value(false));
  }
}
