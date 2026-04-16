package com.ds.dsfest.global.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    long startTime = System.currentTimeMillis();

    filterChain.doFilter(request, response);

    long duration = System.currentTimeMillis() - startTime;
    String query = request.getQueryString() != null ? "?" + request.getQueryString() : "";

    log.info(
        "[{}] {}{} → {} ({}ms)",
        request.getMethod(),
        request.getRequestURI(),
        query,
        response.getStatus(),
        duration);
  }

  /** 에러 디스패치(/error 포워드) 시 중복 로그 방지 */
  @Override
  protected boolean shouldNotFilterErrorDispatch() {
    return true;
  }
}
