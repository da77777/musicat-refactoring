package com.example.musicat.controller;

import com.example.musicat.domain.etc.ChatVO;
import com.example.musicat.repository.etc.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
//@Log4j2
public class RoomController {


    private final SimpMessagingTemplate template;
    private final ChatRoomRepository repository;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ModelAndView rooms(){

        //log.info("# All Chat Rooms");
        System.out.println("# All Chat Rooms");
        ModelAndView mv = new ModelAndView("view/member/rooms");

        mv.addObject("list", repository.findAllRooms());

        return mv;
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public String createRoom(@RequestParam String name, RedirectAttributes rttr){

        log.info("# Create Chat Room , name: " + name);
        //System.out.println("# Create Chat Room , name: " + name);
        rttr.addFlashAttribute("roomName", repository.createChatRoomDTO(name).getName());
        return "redirect:/chat/rooms";
    }

    //채팅방 삭제
    @PostMapping(value="/room/delete")
    public String deleteRoom(@RequestParam String roomId, RedirectAttributes rttr) {

        log.info("# Delete Chat Room, name : " + roomId);
        rttr.addFlashAttribute("deleteSuccess", repository.deleteChatRoom(roomId));

        ChatVO message = new ChatVO();
        message.setUserOuted(true);
        template.convertAndSend("/sub/topic/chat/room/" + roomId, message);
        return "redirect:/chat/rooms";
    }
    //채팅방 조회
    @GetMapping("/room")
    //public String getRoom(@RequestParam String roomId, @RequestParam String username, Model model){
    public String getRoom(@RequestParam String roomId, Model model){

        log.info("# get Chat Room, roomID : " + roomId);
        //System.out.println("# get Chat Room, roomID : " + roomId);
        //System.out.println("# get username, username : " + username);
        model.addAttribute("room", repository.findRoomById(roomId));
        //model.addAttribute("username", username);
        return "view/member/room";
    }
}