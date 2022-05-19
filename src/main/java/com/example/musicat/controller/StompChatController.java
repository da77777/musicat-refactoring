package com.example.musicat.controller;

import com.example.musicat.domain.etc.ChatVO;
import com.example.musicat.domain.etc.NoteVO;
import com.example.musicat.domain.etc.NotifyVO;
import com.example.musicat.exception.customException.InvalidNotifyException;
import com.example.musicat.websocket.manager.NotifyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    private final NotifyManager notifyManager;

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "topic/chat/enter")
    public void enter(ChatVO message){
        log.info("stomp : topic/chat/enter");
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        message.setSystem(true);
        template.convertAndSend("/sub/topic/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "topic/chat/message")
    public void message(ChatVO message) {
        log.info("stomp : topic/chat/message");
        template.convertAndSend("/sub/topic/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value="topic/notify/register")
    public void notifyRegister(Integer no) {
        log.info("stomp : /topic/notify");
        log.info("memberNo : {}", no);
//        template.convertAndSend("/sub/topic/notify/" + member.getNotifyId(),
//                "notify registered : " + member.getNotifyId());
        notifyManager.publishNotify(no);
    }

    @GetMapping(value="/notifyRead/{notify_no}")
    public String notifyRead(@PathVariable int notify_no){
        log.info("notifyRead");

        NotifyVO notifyVO = notifyManager.selectNotifyOne(notify_no);
        if(notifyVO == null) {
            throw new InvalidNotifyException("해당 알림 메시지가 존재하지 않습니다.");
        }

        notifyManager.updateNotifyRead(notifyVO.getNotify_no());

        String link = notifyVO.getLink();

        log.info("notify redirect:"+ link);
        return "redirect:/" + link;
    }
}
