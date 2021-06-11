package fr.esgi.projetannuel.repository;

import fr.esgi.projetannuel.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findById(String id);
    void deleteById(String id);
}
