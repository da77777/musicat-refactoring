package com.example.musicat.websocket.manager;

import com.example.musicat.domain.etc.NotifyVO;
import com.example.musicat.service.etc.NotifyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@RequiredArgsConstructor
@Component
public class NotifyManager {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    Map<Integer, String> notifyMap;

    @Autowired
    NotifyService notifyService;

    @Autowired
    private StompHandler stompHandler;

    @PostConstruct
    private void init() {
        notifyMap = new HashMap<Integer, String>();
    }

    public void addToNotifyList(int memberNo, String notifyId) {
        notifyMap.put(memberNo, notifyId);
    }

    public void deleteFromNotifyList(int memberNo) {
        log.info("deleteNotify : " + memberNo);
        notifyMap.remove(memberNo);
    }

    public String getNotifyId(int memberNo) {
        return notifyMap.get(memberNo);
    }

    public void addNotify(NotifyVO notifyVo) {
        log.info("addNotify : " + notifyVo.toString());
        notifyService.insertNotify(notifyVo);
        publishNotify(notifyVo.getMember_no());

    }
    public void updateNotifyRead(int notify_no) {
        notifyService.updateNotifyRead(notify_no);
    }

    public void publishNotify(int memberId) {

        log.info("sendNotify entered : " + memberId);
        String notifyId = getNotifyId(memberId);

        log.info(notifyMap.toString());
        log.info("string notifyId : " + notifyId);

        if (notifyId == null)
            return;

        log.info("notifyManager : member notifyId is not Null");
        List<NotifyVO> notifyList = notifyService.selectNotify(memberId);

        template.convertAndSend("/sub/topic/notify/" + notifyId, notifyList);
    }

    public NotifyVO selectNotifyOne(int notify_no) {
        return notifyService.selectNotifyOne(notify_no);
    }
}
