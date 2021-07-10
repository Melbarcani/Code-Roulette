package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.Game;
import fr.esgi.projetannuel.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> findAll(){
        return new ResponseEntity<>(gameService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> findById(@PathVariable String id){
        return new ResponseEntity<>(gameService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/games/{userId}")
    public ResponseEntity<List<Game>> findByUserId(@PathVariable String userId){
        return new ResponseEntity<>(gameService.findByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Game> save(@RequestBody Game game){
        return new ResponseEntity<>(gameService.create(game), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        gameService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/endTurn")
    public ResponseEntity<Game> endTurn(@RequestBody Game game){
        return new ResponseEntity<>(gameService.endTurn(game), HttpStatus.CREATED);
    }
}
