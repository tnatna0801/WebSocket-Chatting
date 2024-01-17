package com.gugu.heychat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoomDTO implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;
    private long userCount;

    @Builder
    public ChatRoomDTO(String name) {
        this.roomId = UUID.randomUUID().toString();
        this.name = name;
    }
}
