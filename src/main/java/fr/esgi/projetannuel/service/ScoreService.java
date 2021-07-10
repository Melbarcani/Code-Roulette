package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.model.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ExerciseService exerciseService;

    public long computeScore(Exercise exercise, long instructionsCount, long spentTime) {
        long initialInstructionsCount = exerciseService.getExercise(exercise.getId()).getInitialInstructionsCount();
        return (initialInstructionsCount - instructionsCount) * spentTime /2 ;
    }
}
