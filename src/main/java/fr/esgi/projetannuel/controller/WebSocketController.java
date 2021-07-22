package fr.esgi.projetannuel.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.esgi.projetannuel.model.Message;
import fr.esgi.projetannuel.service.ChatService;
import fr.esgi.projetannuel.service.GameService;
import fr.esgi.projetannuel.service.MessageService;
import fr.esgi.projetannuel.service.UserService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
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

    @MessageMapping("/socket/sendMessage")
    @SendTo("/socket/chat")
    public Message sendMessage(@RequestBody ObjectNode objectJson) {
        String chatId = objectJson.get("chatId").asText();
        String messageContent = objectJson.get("message").asText();
        String userId = objectJson.get("userId").asText();

        Message message = messageService.create(new Message(messageContent, userService.findById(userId)));
        chatService.addMessage(chatService.findById(chatId), message);

        return message;
    }

    @MessageMapping("/socket/updateQueueCounter")
    @SendTo("/socket/updateQueueCounter")
    public int updateQueueCounter() {
        return 0;
    }

    @MessageMapping("/socket/endTurn/{gameId}")
    @SendTo("/socket/game/update/{gameId}")
    public int updateGame(@DestinationVariable String gameId) {
        return 0;
    }

    @MessageMapping("/socket/updateLobby/{lobbyId}")
    @SendTo("/socket/lobby/update/{lobbyId}")
    public int updateLobby(@DestinationVariable String lobbyId) {
        return 0;
    }

    @MessageMapping("/socket/gameCreated/{lobbyId}/{gameId}")
    @SendTo("/socket/lobby/update/{lobbyId}")
    public String redirectToGame(@DestinationVariable String lobbyId, @DestinationVariable String gameId) {
        return gameId;
    }
}
