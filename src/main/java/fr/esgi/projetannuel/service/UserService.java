package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Constants;
import fr.esgi.projetannuel.model.Session;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.SessionRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final SessionRepository sessionRepository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.USER, id));
    }

    public Session findByToken(String token) {
        return sessionRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("session", token));
    }

    public User create(User user) {
        return repository.save(user);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public User updateElo(User user, int elo) {
        user.setElo(user.getElo() + elo);
        return repository.save(user);
    }
}
