package fr.esgi.projetannuel.repository;

import fr.esgi.projetannuel.model.UserInGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInGameRepository extends JpaRepository<UserInGame, Integer> {
    Optional<UserInGame> findById(String id);
    void deleteById(String id);
}
