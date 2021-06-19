package fr.esgi.projetannuel.service.compiler;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JavaAdapterService extends AbstractCompilerService {

    private static final String SPLITTER = "//&&&&&&&";

    @Override
    public String createExerciseToDisplay(String exoId, String content) {
        return super.createExerciseToDisplay(exoId, content, SPLITTER);
    }

    @Override
    public String mergeCode(Exercise userExercise, Exercise mainExercise) {
        return mergeCode(userExercise, mainExercise, SPLITTER);
    }
}
