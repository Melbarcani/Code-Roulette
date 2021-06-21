package fr.esgi.projetannuel.repository;

import fr.esgi.projetannuel.model.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LobbyRepository extends JpaRepository<Lobby, Integer> {
    Optional<Lobby> findById(String id);
    void deleteById(String id);
}
