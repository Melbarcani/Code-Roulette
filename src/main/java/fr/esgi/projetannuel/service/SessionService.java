package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Session;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.SessionRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository repository;
    private final UserRepository userRepository;

    public List<Session> findAll() {
        return repository.findAll();
    }

    public Session findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Session", id));
    }

    public Session create(Session session) {
        return repository.save(session);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("session", "sessionId"));
    }
}
