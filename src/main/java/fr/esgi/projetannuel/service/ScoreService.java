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

    public long computeScore(CodeResult codeResult, Game game, long timer) {
        var instructionsCount = codeResult.getInstructionsCount();
        var lastCompilation = game.getCompilations().size() - 1;
        var lastInstructions = exerciseService.getExercise(game.getExercise().getId()).getInitialInstructionsCount();

        if (lastCompilation > -1) {
            lastInstructions = game.getCompilations().get(lastCompilation).getInstructionsCount();
        }
        long score = (lastInstructions - instructionsCount) - (game.getTimer() - timer) * 2;
        return instructionsCount == lastInstructions || score < 0
                ? 0
                : score;
    }

    public long computeScore(Exercise exercise, long instructionsCount, long spentTime, long lastScore) {
        long initialInstructionsCount = exerciseService.getExercise(exercise.getId()).getInitialInstructionsCount();
        return ((initialInstructionsCount - instructionsCount) - lastScore) * 2 / spentTime ;
    }
}
