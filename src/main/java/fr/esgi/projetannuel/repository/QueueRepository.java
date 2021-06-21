package fr.esgi.projetannuel.repository;

import fr.esgi.projetannuel.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueRepository extends JpaRepository<Queue, Integer> {
    Optional<Queue> findById(String id);
    void deleteById(String id);
}
