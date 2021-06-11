package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Session;
import fr.esgi.projetannuel.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository repository;

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
}
