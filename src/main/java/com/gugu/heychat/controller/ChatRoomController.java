package com.gugu.heychat.controller;

import com.gugu.heychat.dto.LoginDTO;
import com.gugu.heychat.repository.ChatRoomRepository;
import com.gugu.heychat.service.ChatService;
import com.gugu.heychat.dto.ChatRoomDTO;
import com.gugu.heychat.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    //private final ChatService chatService;
    private final ChatRoomRepository chatRoomRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인한 회원의 id 및 jwt 토큰 정보를 조회할 수 있도록 함
     * @return
     */
    @GetMapping("/user")
    @ResponseBody
    public LoginDTO getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return LoginDTO.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
    }

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
    public ChatRoomDTO createRoom(@RequestParam(value="name") String name) {
        log.info("방 이름 {} : ", name);
        return chatRoomRepository.createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable(value="roomId") String roomId) {
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomDTO roomInfo(@PathVariable(value="roomId") String roomId) {
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
