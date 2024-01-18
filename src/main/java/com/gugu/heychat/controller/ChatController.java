package com.gugu.heychat.controller;

import com.gugu.heychat.dto.ChatMessageDTO;
import com.gugu.heychat.repository.ChatRoomRepository;
import com.gugu.heychat.service.ChatService;
import com.gugu.heychat.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    /*
     websocket "/pub/chat/message"로 들어오는 메세지를 처리한다.
     이때 클라이언트에서는 /app/chat/message 로 요청하게 되고 이것을 controller 가 받아서 처리한다. (pub)
     처리가 완료되면 /topic/chat/room/roomId 로 메시지가 전송된다. (sub)
     */
    @MessageMapping("/chat/message") // websocket으로 들어오는 메세지 발행을 처리한다.
    public void message(ChatMessageDTO message, @Header("token") String token) {
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);

        log.info("발신 message : {}", message);
        // 채팅방 인원수 세팅
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
        // WebSocket에 발행된 메시지를 redis로 발핸
        chatService.sendChatMessage(message);
    }
}
