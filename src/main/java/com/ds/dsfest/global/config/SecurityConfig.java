package com.ds.dsfest.global.config;

import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.ds.dsfest.global.exception.GlobalErrorCode;
import com.ds.dsfest.global.jwt.JwtAuthenticationFilter;
import com.ds.dsfest.global.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsConfigurationSource corsConfigurationSource;
  private final ObjectMapper objectMapper;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        .authorizeHttpRequests(
            auth ->
                auth
                    // Swagger UI
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                    .permitAll()
                    // WebSocket
                    .requestMatchers("/ws", "/ws/**")
                    .permitAll()
                    // 어드민 로그인은 누구나 접근 가능
                    .requestMatchers(HttpMethod.POST, "/api/admin/login")
                    .permitAll()
                    // 그 외 어드민 엔드포인트는 인증 필요
                    .requestMatchers("/api/admin/**")
                    .authenticated()
                    // 사용자 공개 엔드포인트
                    .anyRequest()
                    .permitAll())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(
            exception ->
                exception
                    .authenticationEntryPoint(
                        (request, response, e) -> {
                          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                          response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                          response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                          response
                              .getWriter()
                              .write(
                                  objectMapper.writeValueAsString(
                                      ApiResponse.onFailure(GlobalErrorCode.UNAUTHORIZED)));
                        })
                    .accessDeniedHandler(
                        (request, response, e) -> {
                          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                          response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                          response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                          response
                              .getWriter()
                              .write(
                                  objectMapper.writeValueAsString(
                                      ApiResponse.onFailure(GlobalErrorCode.FORBIDDEN)));
                        }));

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
