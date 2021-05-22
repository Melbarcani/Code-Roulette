package fr.esgi.projetannuel.repository;

import fr.esgi.projetannuel.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Integer> {
    Optional<Code> findById(String id);
    void deleteById(String id);
}
