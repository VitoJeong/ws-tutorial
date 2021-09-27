package com.websocket.wstutorial.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // 환경 구성을 위한 설정파일로 등록
@EnableWebSocketMessageBroker // Websocket을 통한 메시징 기능을 활성화
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Websocket 연결을 위한 엔드포인트를 지정
        registry.addEndpoint("/stomp-endpoint")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트가 Subscribe 할떄(Server -> Client 메시지 전송 시 Endpoint)
        registry.enableSimpleBroker("/topic");
        // 서버가 목적지 일때(Client -> Server 메시지 전송시 Endpoint)
        registry.setApplicationDestinationPrefixes("/app");
    }
}
