package fr.esgi.projetannuel.repository;

import fr.esgi.projetannuel.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Optional<Exercise> findById(String id);
    void deleteById(String id);
    Exercise findExerciseByTitle(String title);
    Optional<Exercise> findExerciseById(String id);
}
