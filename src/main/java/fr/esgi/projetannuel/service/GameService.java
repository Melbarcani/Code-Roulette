package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Game;
import fr.esgi.projetannuel.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository repository;

    public List<Game> findAll() {
        return repository.findAll();
    }

    public Game findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game", id));
    }

    public Game create(Game game) {
        return repository.save(game);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
