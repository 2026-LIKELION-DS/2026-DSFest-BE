package com.ds.dsfest.global.config;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfig {

  private static final ZoneId FESTIVAL_ZONE = ZoneId.of("Asia/Seoul");

  @Bean
  public Clock festivalClock(@Value("${festival.fake-now:}") String fakeNow) {
    if (fakeNow == null || fakeNow.isBlank()) {
      return Clock.system(FESTIVAL_ZONE);
    }
    LocalDateTime fixed = parseFakeNow(fakeNow.trim());
    return Clock.fixed(fixed.atZone(FESTIVAL_ZONE).toInstant(), FESTIVAL_ZONE);
  }

  private LocalDateTime parseFakeNow(String value) {
    try {
      return LocalDateTime.parse(value);
    } catch (Exception ignored) {
    }
    try {
      return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    } catch (Exception ignored) {
    }
    return LocalDateTime.now(Clock.system(FESTIVAL_ZONE))
        .toLocalDate()
        .atTime(LocalTime.parse(value));
  }
}
