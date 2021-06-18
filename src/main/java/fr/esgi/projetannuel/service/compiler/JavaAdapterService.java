package fr.esgi.projetannuel.service.compiler;

import fr.esgi.projetannuel.exception.ExerciseNotSplittedException;
import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JavaAdapterService implements ICompilerService {

    public String createExerciseToDisplay(String exoId, String content) {
        var parts = new String[0];
        if (content != null) {
            parts = content.split(SPLITTER);
        }
        if(parts.length > 0){
            return parts[1];
        }
        throw new ResourceNotFoundException("exo", exoId);
    }

    @Override
    public String mergeCode(Exercise userExercise, Exercise mainExercise) {
        String userCode = userExercise.getCode();
        String mainCode = mainExercise.getCode();

        var mainParts = new String[0];
        if (userCode != null && mainCode != null) {
            mainParts = mainCode.split(SPLITTER);
        }
        if(mainParts.length > 1){
            return mainParts[0] + userCode + mainParts[2];
        }
        throw new ExerciseNotSplittedException("exo", userExercise.getId());
    }
}
