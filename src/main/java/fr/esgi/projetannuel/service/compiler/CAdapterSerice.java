package fr.esgi.projetannuel.service.compiler;

import fr.esgi.projetannuel.model.Exercise;

public class CAdapterSerice extends AbstractCompilerService {

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
