package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.model.CodeResult;
import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ExerciseService exerciseService;

    public long computeScore(CodeResult codeResult, Game game, long instructionsCount){
        return 0;
    }

    public long computeScore(Exercise exercise, long instructionsCount, long spentTime, long lastScore) {
        long initialInstructionsCount = exerciseService.getExercise(exercise.getId()).getInitialInstructionsCount();
        return ((initialInstructionsCount - instructionsCount) - lastScore) * spentTime /2 ;
    }
}
