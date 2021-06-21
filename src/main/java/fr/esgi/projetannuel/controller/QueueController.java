package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.Queue;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.service.QueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queue")
public class QueueController {
    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping
    public ResponseEntity<List<Queue>> findAll(){
        return new ResponseEntity<>(queueService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Queue> findById(@PathVariable String id){
        return new ResponseEntity<>(queueService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/joinQueue")
    public ResponseEntity<List<User>> joinQueue(){
        return new ResponseEntity<>(queueService.joinQueue(), HttpStatus.OK);
    }

    @DeleteMapping("/leaveQueue")
    public ResponseEntity<?> leaveQueue() {
        queueService.leaveQueue();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
