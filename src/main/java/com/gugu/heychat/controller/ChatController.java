package com.gugu.heychat.controller;

import com.gugu.heychat.dto.ChatMessageDTO;
import com.gugu.heychat.repository.ChatRoomRepository;
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
@RestController
@Slf4j
public class ChatController {

    //private final SimpMessageSendingOperations messagingTemplate;
    //private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChannelTopic channelTopic;

    /*
     MessageMapping 을 통해 webSocket 로 들어오는 메시지를 발신 처리한다.
     이때 클라이언트에서는 /app/chat/message 로 요청하게 되고 이것을 controller 가 받아서 처리한다. (pub)
     처리가 완료되면 /topic/chat/room/roomId 로 메시지가 전송된다. (sub)
     */
    @MessageMapping("/chat/message") // websocket으로 들어오는 메세지 발행을 처리한다.
    public void message(ChatMessageDTO message, @Header("token") String token) {
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);

        log.info("발신 message : {}", message);

        // 채팅방 입장시에는 대화명과 메시지를 자동으로 세팅한다.
        if (ChatMessageDTO.MessageType.ENTER.equals(message.getType())) {
            message.setSender("[알림]");
//            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(nickname + "님이 입장하셨습니다.");
        }
        // WebSocket에 발행된 메세지를 redis로 발행(publish)
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
