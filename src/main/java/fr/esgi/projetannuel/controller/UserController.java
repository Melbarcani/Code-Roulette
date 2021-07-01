package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final WebSocketController webSocketController;
    private final UserService userService;

    public UserController(WebSocketController webSocketController, UserService userService) {
        this.webSocketController = webSocketController;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<User> findByToken(@PathVariable String token){
        return new ResponseEntity<>(userService.findByToken(token).getUser(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> save(@RequestBody User user){
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/updateElo/{id}")
    public ResponseEntity<User> compileById(@PathVariable String id, @RequestBody int elo){
        return new ResponseEntity<>(userService.updateElo(userService.findById(id), elo), HttpStatus.OK);
    }

    @PostMapping("/joinQueue")
    public ResponseEntity<List<User>> joinQueue(){
        var result = userService.joinQueue();
        // webSocketController.updateQueueCounter();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/leaveQueue")
    public ResponseEntity<?> leaveQueue() {
        userService.leaveQueue();
        // webSocketController.updateQueueCounter();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/usersInQueue")
    public ResponseEntity<Long> countUsersInQueue() {
        return new ResponseEntity<>(userService.usersInQueue(), HttpStatus.OK);
    }

}
