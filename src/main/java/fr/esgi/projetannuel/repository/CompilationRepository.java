package fr.esgi.projetannuel.repository;

import fr.esgi.projetannuel.model.Compilation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    Optional<Compilation> findById(String id);
    void deleteById(String id);
}
