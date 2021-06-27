package fr.esgi.projetannuel.repository;

import fr.esgi.projetannuel.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findById(String id);
    void deleteById(String id);
}
