package com.websocket.wstutorial.chat;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {
    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    // 객체가 생성된 후 별도의 초기화 작업을 위해 실행
    public List<ChatRoom> findAllRoom() {
        List chatRooms = new ArrayList(chatRoomMap.values());

        Collections.reverse(chatRooms);
        return chatRooms;
    }

    // Id에 해당하는 룸을 찾는다.
    public ChatRoom findRoomById(String id) {
        ChatRoom chatRoom = chatRoomMap.get(id);
        if (chatRoom == null) {
            throw new IllegalArgumentException(String.format("Invalid room ID: %s", id));
        }

        return chatRoom;
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);

        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

}
