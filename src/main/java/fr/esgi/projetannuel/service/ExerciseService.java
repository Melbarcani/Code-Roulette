package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseService {
    private final ExerciseRepository repository;

    public List<Exercise> findAll() {
        return repository.findAll();
    }

    public Exercise findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exercise", id));
    }

    public Exercise create(Exercise exercise) {
        return repository.save(exercise);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
