package com.gugu.heychat.repository;

import com.gugu.heychat.dto.ChatRoomDTO;
import com.gugu.heychat.pubsub.RedisSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 채팅방 정보가 초기화 되지 않도록 생성시 Redis Hash에 저장하도록 처리한다.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomRepository {


    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoomDTO> opsHashChatRoom;
    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
    }
    // 모든 채팅방 조회
    public List<ChatRoomDTO> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }
    // 특정 채팅방 조회
    public ChatRoomDTO findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }
    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
    public ChatRoomDTO createChatRoom(String name) {
        ChatRoomDTO chatRoom = ChatRoomDTO.builder().name(name).build();
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
