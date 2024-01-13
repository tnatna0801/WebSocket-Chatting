package com.gugu.heychat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gugu.heychat.service.ChatService;
import com.gugu.heychat.dto.ChatMessageDTO;
import com.gugu.heychat.dto.ChatRoomDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

//@Slf4j
//@Component
//@RequiredArgsConstructor
public class HeychatSocketHandler { //extends TextWebSocketHandler {
//
//    private final ObjectMapper objectMapper;
//    private final ChatService chatService;
//
//    private static final ConcurrentHashMap<String, WebSocketSession> clients = new ConcurrentHashMap<String, WebSocketSession>();
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload {}", payload); // log 확인
//        //TextMessage textMessage = new TextMessage("Hey chat! ;)");
//
//        ChatMessageDTO chatMessage = objectMapper.readValue(payload, ChatMessageDTO.class);
//        log.info("session : {}",chatMessage.toString());
//
//        ChatRoomDTO room = chatService.findRoomById(chatMessage.getRoomId());
//        log.info("room : {}",room.toString());
//
//        room.handleActions(session, chatMessage, chatService);
//    }
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        log.info(session + " 유저 접속");
//        //clients.put(session.getId(), session);
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        log.info(session + " 유저 접속 해제");
//        //clients.remove(session.getId());
//    }
}
