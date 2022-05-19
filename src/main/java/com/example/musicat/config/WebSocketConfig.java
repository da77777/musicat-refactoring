package com.example.musicat.config;

import com.example.musicat.websocket.manager.StompHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
//@EnableWebSocket // web socket 활성화
//public class WebSocketConfig implements WebSocketConfigurer {
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//    private final ChatHandler chatHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // websocket에 접속하기 위한 endpoint
//        // 도메인이 다른 서버에서도 접속 가능하도록 CORS:setAllowedOrigins("*") 추가.
//        // 이제 클라이언트가 localhost:8080/chat 으로 메세지 통신할 수 있는 준비가 됨.
//        registry.addHandler(chatHandler, "ws/chat")
//                .setAllowedOriginPatterns("*")
//                .withSockJS()
//                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js");
//        //.withSockJS() 추가
//        //setAllowedOrigins("*")에서 *라는 와일드 카드를 사용하면
//        //보안상의 문제로 전체를 허용하는 것보다 직접 하나씩 지정해주어야 한다고 한다.
//    }


    private final StompHandler stompHandler;

    WebSocketConfig(StompHandler stompHandler) {
        this.stompHandler = stompHandler;
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/topic")
                .setAllowedOrigins("http://localhost:8080")
                .withSockJS();
    }

    /*어플리케이션 내부에서 사용할 path를 지정할 수 있음*/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");
    }
}