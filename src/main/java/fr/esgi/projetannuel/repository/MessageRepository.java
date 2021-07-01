package fr.esgi.projetannuel.repository;


import fr.esgi.projetannuel.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    Optional<Message> findById(String id);
    void deleteById(String id);
}
