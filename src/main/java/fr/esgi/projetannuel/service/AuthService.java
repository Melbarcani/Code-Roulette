package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Login;
import fr.esgi.projetannuel.model.Session;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.SessionRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import fr.esgi.projetannuel.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository, SessionRepository sessionRepository, PasswordEncoder passwordEncoder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public HttpHeaders createSession(Login login){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                login.getEmail(),
                login.getPassword());

        User user = findUserByEmail(login.getEmail());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String token = tokenProvider.createToken(authentication);

        sessionRepository.save(new Session(user.getId(), null, token, user));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return httpHeaders;
    }

    public URI registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User userSaved = userRepository.save(user);
        String id = userSaved.getId();
        return ServletUriComponentsBuilder.fromPath("/api/users").path("/{id}").buildAndExpand(id).toUri();
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", email));
    }
}
