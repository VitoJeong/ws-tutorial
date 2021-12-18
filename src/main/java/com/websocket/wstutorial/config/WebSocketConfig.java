package com.websocket.wstutorial.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket // WebSocket을 활성화
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        ///ws/chat Endpoint로 Handshake가 이루어짐

        // 커스텀한 핸들러를 등록
        registry.addHandler(chatHandler, WebSocketInfo.CHAT_PATH)
                .setAllowedOrigins("http://localhost:8080", "http://localhost:8080/*");
                // .withSockJS()
                // .setClientLibraryUrl("http://localhost:8080/js/sockjs.min.js");

        registry.addHandler(chatHandler, WebSocketInfo.MULTI_CHAT_PATH)
                .setAllowedOrigins("http://localhost:8080", "http://localhost:8080/*");
    }

    //setAllowedOrigins("*")에서 *라는 와일드 카드를 사용하면
    //보안상의 문제로 전체를 허용하는 것보다 직접 하나씩 지정해주어야 한다고 한다.

    // 웹소켓과 관련된 정보
    public static class WebSocketInfo {
        private static final String CHAT_PATH = "/ws/chat";
        private static final String MULTI_CHAT_PATH = "/ws/multi-chat";
    }
}