package com.gugu.heychat.service;

import com.gugu.heychat.dto.ChatMessageDTO;
import com.gugu.heychat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChannelTopic channelTopic; //
    private final RedisTemplate redisTemplate;
//    private Map<String, ChatRoomDTO> chatRooms; // 서버에 생성된 모든 채팅방의 정보
    private final ChatRoomRepository chatRoomRepository;

    /*
    destination 정보에서 roomId 추출
     */
    public String getRoomId(String destination) {
        log.info("destination {} : ", destination);
        int lastIndex = destination.lastIndexOf('/');
        if(lastIndex != -1) {
            log.info("get roomId index : {}", lastIndex);
            return destination.substring(lastIndex+1);
        }
        else
            return "";
    }

    // 지정된 Websocket 세션에 메세지 발송
    public void sendChatMessage(ChatMessageDTO chatMessage) {
        chatMessage.setUserCount(chatRoomRepository.getUserCount(chatMessage.getRoomId()));
        if(ChatMessageDTO.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에 입장했습니다.");
            chatMessage.setSender("[알림]");
        }
        else if (ChatMessageDTO.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            chatMessage.setSender("[알림]");
        }
        log.info(" Service chatMessage 촤핫 메세지 뭐게~~: {}", chatMessage.getMessage());
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }
}