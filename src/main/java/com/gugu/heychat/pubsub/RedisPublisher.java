//package com.gugu.heychat.pubsub;
//
//import com.gugu.heychat.dto.ChatMessageDTO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.stereotype.Service;
//
///*
//채팅방에 입장하여 메시지를 작성하면 해당 메시지를 Redis Topic에 발행하는 서비스
// */
//@RequiredArgsConstructor
//@Service
//@Slf4j
//public class RedisPublisher {
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public void publish(ChannelTopic topic, ChatMessageDTO message) { // 발행
//        redisTemplate.convertAndSend(topic.getTopic(), message);
//    }
//}