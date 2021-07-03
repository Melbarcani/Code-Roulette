package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.UserInGame;
import fr.esgi.projetannuel.service.UserInGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userInGame")
public class UserInGameController {
    private final UserInGameService userInGameService;

    public UserInGameController(UserInGameService userInGameService) {
        this.userInGameService = userInGameService;
    }

    @GetMapping
    public ResponseEntity<List<UserInGame>> findAll(){
        return new ResponseEntity<>(userInGameService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInGame> findById(@PathVariable String id){
        return new ResponseEntity<>(userInGameService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserInGame> save(@RequestBody UserInGame userInGame){
        return new ResponseEntity<>(userInGameService.create(userInGame), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userInGameService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
