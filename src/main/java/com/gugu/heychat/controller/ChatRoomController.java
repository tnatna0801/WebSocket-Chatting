package com.gugu.heychat.controller;

import com.gugu.heychat.repository.ChatRoomRepository;
import com.gugu.heychat.service.ChatService;
import com.gugu.heychat.dto.ChatRoomDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    //private final ChatService chatService;
    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/room")
    public String rooms(Model model) {
        return "chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoomDTO> room() {
        return chatRoomRepository.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoomDTO createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomDTO roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

//    @PostMapping
//    public ChatRoomDTO createRoom(@RequestParam String name) {
//        log.debug("chat room name : {}", name);
//        return chatService.createRoom(name);
//    }
//
//    @GetMapping
//    public List<ChatRoomDTO> findAllRoom() {
//        return chatService.findAllRoom();
//    }
}
