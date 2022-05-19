package com.example.musicat.security;


import com.example.musicat.websocket.manager.NotifyManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

//세션 만료 시
@Slf4j
@Component
public class SessionDestroyListener implements ApplicationListener<SessionDestroyedEvent> {

    @Autowired
    NotifyManager notifyManager;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        List<SecurityContext> securityContextsList = event.getSecurityContexts();

        for(SecurityContext securityContext : securityContextsList) {
            MemberAccount memberAccount = (MemberAccount) securityContext.getAuthentication().getPrincipal();

            //세션이 종료된 회원의 번호
            notifyManager.deleteFromNotifyList(memberAccount.getMemberVo().getNo());
        }
    }
}