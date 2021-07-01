package fr.esgi.projetannuel.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.esgi.projetannuel.model.Chat;
import fr.esgi.projetannuel.model.Message;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.service.ChatService;
import fr.esgi.projetannuel.service.MessageService;
import fr.esgi.projetannuel.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebSocketController {

    private final MessageService messageService;
    private final ChatService chatService;
    private final UserService userService;

    public WebSocketController(UserService userService, ChatService chatService, MessageService messageService) {
        this.userService = userService;
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @MessageMapping("/hello")
    @SendTo("/socket/chat")
    public Message greeting(@RequestBody ObjectNode objectJson) {
        String chatId = objectJson.get("chatId").asText();
        String messageContent = objectJson.get("message").asText();
        String userId = objectJson.get("userId").asText();

        Message message = messageService.create(new Message(messageContent, userService.findById(userId)));
        chatService.addMessage(chatService.findById(chatId), message);

        return message;
    }
}
