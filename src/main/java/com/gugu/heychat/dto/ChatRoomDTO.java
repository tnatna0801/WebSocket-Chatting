package com.gugu.heychat.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class ChatRoomDTO implements Serializable {

    // 직렬화 => 왜 하는 걸까? Redis에 저장되는 객체들은 Serialize 가능해야한다.
    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;
//    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoomDTO(String name) {
        this.roomId = UUID.randomUUID().toString();
        this.name = name;
    }

//    public void handleActions(WebSocketSession session, ChatMessageDTO chatMessage, ChatService chatService) {
//        if (chatMessage.getType().equals(ChatMessageDTO.MessageType.ENTER)) { //입장이라면
//            sessions.add(session); // 채팅룸 세션 정보에 크라이언트의 세션 리스트에 추가
//            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
//        }
//        sendMessage(chatMessage, chatService);
//    }
//
//    public <T> void sendMessage(T message, ChatService chatService) {
//        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message)); // 메세지가 도착할 경우 채팅룸의 모든 session에 메시지 발송
//    }
}
