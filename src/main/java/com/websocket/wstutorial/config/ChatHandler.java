package com.websocket.wstutorial.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.wstutorial.chat.ChatMessage;
import com.websocket.wstutorial.chat.ChatRoom;
import com.websocket.wstutorial.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 다수의 클라이언트가 보낸 메세지를 처리하는 핸들러
 * <p>
 * TextWebSocketHandler: 텍스트 기반의 채팅을 구현하기 위한 클래스
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> socketSessionList = new ArrayList<>();

    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;

    // @Override
    // protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    //     // payload: 보내고자 하는 데이터 자체를 의미
    //     String payload = message.getPayload();
    //     log.info("payload : " + payload);
    //
    //     // 접속된 모든 클라이언트에게 메세지를 보낸다.
    //     for(WebSocketSession socketSession: socketSessionList) {
    //         socketSession.sendMessage(message);
    //     }
    // }

    @Override

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("메세지 전송 = {} : {}", session, message.getPayload());
        String msg = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);
        ChatRoom chatRoom = chatRoomRepository.findRoomById(chatMessage.getChatRoomId());
        chatRoom.handleMessage(session, chatMessage, objectMapper);
    }

    //Client가 접속 시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        socketSessionList.add(session);

        log.info(session + " 클라이언트 접속");
    }

    //Client가 접속 해제 시 호출되는 메서드드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info(session + " 클라이언트 접속 해제");
        socketSessionList.remove(session);
    }
}