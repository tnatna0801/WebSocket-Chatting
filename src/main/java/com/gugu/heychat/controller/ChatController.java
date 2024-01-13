package com.gugu.heychat.controller;

import com.gugu.heychat.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
//@RequestMapping("/chat")
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message") // websocket으로 들어오는 메세지 발행을 처리한다.
    public void message(ChatMessageDTO message) {
        log.info("발신 message : {}", message);
        if (ChatMessageDTO.MessageType.ENTER.equals(message.getType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
    }
}
