package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
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
}
