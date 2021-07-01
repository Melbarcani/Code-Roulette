package fr.esgi.projetannuel.repository;

import fr.esgi.projetannuel.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    Optional<Chat> findById(String id);
    void deleteById(String id);
}
