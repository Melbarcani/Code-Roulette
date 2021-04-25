package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> save(@RequestBody User user){
        userService.create(user);
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }
}
