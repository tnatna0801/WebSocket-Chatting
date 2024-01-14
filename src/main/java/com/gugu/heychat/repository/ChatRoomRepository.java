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

import java.util.*;

/**
 * 채팅방 정보가 초기화 되지 않도록 생성시 Redis Hash에 저장하도록 처리한다.
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

    // 채팅방(topic)에 발행되는 메시지를 처리할 Listener
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoomDTO> opsHashChatRoom;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<ChatRoomDTO> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
        // 채팅방 생성순서 최근 순으로 반환
//        List chatRooms = new ArrayList<>(chatRoomMap.values());
//        Collections.reverse(chatRooms);
//        return chatRooms;
    }

    public ChatRoomDTO findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    /**
     * 채팅방 생성: 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     * @param name: 채팅방 이름
     * @return ChatRoomDTO: 채팅방 정보를 담은 dto 객체
     */
    public ChatRoomDTO createChatRoom(String name) {
        log.info("방이름 : {}", name);
        try {
            ChatRoomDTO chatRoom = ChatRoomDTO.builder().name(name).build();
            opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
            return chatRoom;
        } catch (Exception e){
            log.error("방생성 error : {}", e.getMessage());
        }
        return null;
    }

    /**
     * 채팅방 입장: redis에 topic을 만들고 pub/sub 통신을 하기 위해 라스너를 설정한다.
     * @param roomId
     */
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if(topic == null){
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }


    public ChannelTopic getTopic(String roomId) {
        log.info("topic : " + roomId);
        log.info("topic : " + topics.get(roomId));
        log.info("topic : " + topics);
        return topics.get(roomId);
    }
}
