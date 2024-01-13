package com.gugu.heychat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDTO {
    public enum MessageType {
        ENTER, TALK
    }
    private MessageType type; // 메세지 타입
    private String roomId; // 채팅 방 번호
    private String sender; // 메세지 보낸사람
    private String message; // 메세지
    //private LocalDateTime date; // 메세지 전송 시간
}
