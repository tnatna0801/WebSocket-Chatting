package com.gugu.heychat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDTO {
    public enum MessageType {
        ENTER, TALK, QUIT
    }
    private MessageType type; // 메세지 타입
    private String roomId; // 채팅 방 번호
    private String sender; // 메세지 보낸사람
    private String message; // 메세지
    private long userCount;
    //private LocalDateTime date; // 메세지 전송 시간

    public ChatMessageDTO() {
    }

    @Builder
    public ChatMessageDTO(MessageType type, String roomId, String sender, String message, long userCount) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.userCount = userCount;
    }
}
