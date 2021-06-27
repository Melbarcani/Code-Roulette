package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.Lobby;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lobby")
public class LobbyController {
    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping
    public ResponseEntity<List<Lobby>> findAll(){
        return new ResponseEntity<>(lobbyService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lobby> findById(@PathVariable String id){
        return new ResponseEntity<>(lobbyService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<User> create(@PathVariable String userId, @RequestBody String lobbyTitle){
        return new ResponseEntity<>(lobbyService.create(userId, lobbyTitle), HttpStatus.CREATED);
    }

    @PostMapping("/joinLobby")
    public ResponseEntity<User> joinLobby(@RequestBody String lobbyId){
        return new ResponseEntity<>(lobbyService.joinLobby(lobbyId), HttpStatus.OK);
    }

    @PostMapping("/leaveLobby")
    public ResponseEntity<?> leaveLobby(@RequestBody String lobbyId){
        lobbyService.leaveLobby(lobbyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        lobbyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
