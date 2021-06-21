package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.Lobby;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/lobbyUsers")
    public ResponseEntity<List<User>> getLobbyUsers(@RequestBody String lobbyId){
        return new ResponseEntity<>(lobbyService.lobbyUsers(lobbyId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Lobby> save(@RequestBody Lobby lobby){
        return new ResponseEntity<>(lobbyService.create(lobby), HttpStatus.CREATED);
    }

    @PostMapping("/joinLobby")
    public ResponseEntity<?> joinLobby(@RequestBody String lobbyId){
        lobbyService.joinLobby(lobbyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/leaveLobby")
    public ResponseEntity<Lobby> leaveLobby(@RequestBody Lobby lobby){
        return new ResponseEntity<>(lobbyService.create(lobby), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        lobbyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
