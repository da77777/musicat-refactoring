package com.example.musicat.config;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        /*
        페이로드 : 전송되는 데이터를 의미. 데이터를 전송할 때 Header와 META 데이터, 에러 체크 비트 등과 같은 다양한 요소들을 함께 보내서
        데이터 전송 효율과 안전성을 높히게 됨. 이 때, 보내고자 하는 데이터 자체를 의미하는것이 페이로드.
        EX) 택배 배송 - 택배 물건은 페이로드, 송장이나 박스는 페이로드 아님.
         */
        //log.info("payload : " + payload);
        System.out.println("payload : " + payload);

        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        list.add(session);

        //log.info(session + " 클라이언트 접속");
        System.out.println(session + " 클라이언트 접속");
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        //log.info(session + " 클라이언트 접속 해제");
        System.out.println(session + " 클라이언트 접속 해제");
        list.remove(session);
    }
}