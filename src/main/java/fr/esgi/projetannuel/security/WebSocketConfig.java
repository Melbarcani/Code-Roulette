package fr.esgi.projetannuel.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins(
                        "http://15.236.20.4",
                        "http://ec2-15-236-20-4.eu-west-3.compute.amazonaws.com",
                        "http://34.248.4.98",
                        "http://ec2-34-248-4-98.eu-west-1.compute.amazonaws.com",
                        "http://localhost:8080",
                        "http://localhost:4200"
                        )
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/api")
                .enableSimpleBroker("/socket");
    }
}
