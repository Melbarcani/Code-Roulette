package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.Chat;
import fr.esgi.projetannuel.model.Message;
import fr.esgi.projetannuel.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<List<Chat>> findAll(){
        return new ResponseEntity<>(chatService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> findById(@PathVariable String id){
        return new ResponseEntity<>(chatService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Chat> save(@RequestBody Chat chat){
        return new ResponseEntity<>(chatService.create(chat), HttpStatus.CREATED);
    }

    @PostMapping("/addMessage")
    public ResponseEntity<Chat> addMessage(@RequestBody Chat chat, Message message){
        return new ResponseEntity<>(chatService.addMessage(chat, message), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        chatService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
