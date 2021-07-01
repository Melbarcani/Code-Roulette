package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.Message;
import fr.esgi.projetannuel.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<Message>> findAll(){
        return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable String id){
        return new ResponseEntity<>(messageService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Message> save(@RequestBody Message message){
        return new ResponseEntity<>(messageService.create(message), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        messageService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
