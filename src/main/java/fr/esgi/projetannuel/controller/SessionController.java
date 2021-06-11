package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.Session;
import fr.esgi.projetannuel.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<Session>> findAll(){
        return new ResponseEntity<>(sessionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> findById(@PathVariable String id){
        return new ResponseEntity<>(sessionService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Session> save(@RequestBody Session session){
        return new ResponseEntity<>(sessionService.create(session), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        sessionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
