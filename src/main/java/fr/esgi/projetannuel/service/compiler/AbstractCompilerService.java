package fr.esgi.projetannuel.service.compiler;

import fr.esgi.projetannuel.exception.ExerciseNotSplittedException;
import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Exercise;

public abstract class AbstractCompilerService {

    public abstract String createExerciseToDisplay(String exoId, String content);

    public abstract String mergeCode(Exercise userExercise, Exercise mainExercise);

    protected String mergeCode(Exercise userExercise, Exercise mainExercise, String splitter) {
        String userCode = userExercise.getCode();
        String mainCode = mainExercise.getCode();

        var mainParts = new String[0];
        if (userCode != null && mainCode != null) {
            mainParts = mainCode.split(splitter);
        }
        if(mainParts.length > 1){
            return mainParts[0] + userCode + mainParts[2];
        }
        throw new ExerciseNotSplittedException("exo", userExercise.getId());
    }
    protected String createExerciseToDisplay(String exoId, String content, String splitter) {
        var parts = new String[0];
        if (content != null) {
            parts = content.split(splitter);
        }
        if (parts.length > 0) {
            return parts[1].replaceAll("(?m)^[ \t]*\r?\n", "");
        }
        throw new ResourceNotFoundException("exo", exoId);
    }
}
