package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Login;
import fr.esgi.projetannuel.model.Session;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.SessionRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import fr.esgi.projetannuel.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    public HttpHeaders createSession(Login login){
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                login.getEmail(),
                login.getPassword());

        var user = findUserByEmail(login.getEmail());
        var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String token = tokenProvider.createToken(authentication);

        sessionRepository.save(new Session(user.getId(), LocalDateTime.now(), token, user));

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return httpHeaders;
    }

    public URI registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var userSaved = userRepository.save(user);
        String id = userSaved.getId();
        return UriComponentsBuilder.fromPath("/api/user").path("/{id}").buildAndExpand(id).toUri();
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", email));
    }
}
