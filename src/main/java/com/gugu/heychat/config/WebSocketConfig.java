package com.gugu.heychat.config;

import com.gugu.heychat.handler.HeychatSocketHandler;
import com.gugu.heychat.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub"); // 클라이언트가 topic을 구독하고 메시지를 숮신할 수 있도록 함
        config.setApplicationDestinationPrefixes("/pub"); //
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/heychat").setAllowedOriginPatterns("*")
                .withSockJS(); // Sockjs를 사용하기 위함 => 웹 소켓을 지원하지 않는 브라우저에서도 비슷한 경험을 제공하기 위해서
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }

    //    private final HeychatSocketHandler myHandler;
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(myHandler, "/heychatHandler").setAllowedOriginPatterns("*");
//    }

//    @Bean
//    public WebSocketHandler myHandler() {
//        return new HeychatSocketHandler();
//    }
}
