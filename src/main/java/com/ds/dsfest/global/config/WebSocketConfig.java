package com.ds.dsfest.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // 클라이언트 구독 경로 (/topic: 브로드캐스트, /queue: 개인)
    registry.enableSimpleBroker("/topic", "/queue");
    // 서버 메시지 전송 prefix
    registry.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // WebSocket 연결 엔드포인트 (SockJS 폴백 포함)
    registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
  }
}
