package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.Login;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.service.AuthService;
import fr.esgi.projetannuel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        HttpHeaders httpHeaders = authService.createSession(login);
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        URI uri = authService.registerUser(user);
        return ResponseEntity.created(uri).build();
    }
}
