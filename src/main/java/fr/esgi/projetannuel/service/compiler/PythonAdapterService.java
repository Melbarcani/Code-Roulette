package fr.esgi.projetannuel.service.compiler;

import fr.esgi.projetannuel.model.Exercise;

public class PythonAdapterService extends AbstractCompilerService {

    private static final String SPLITTER = "##&&&&&&";

    @Override
    public String createExerciseToDisplay(String exoId, String content) {
        return super.createExerciseToDisplay(exoId, content, SPLITTER);
    }

    @Override
    public String mergeCode(Exercise userExercise, Exercise mainExercise) {
        return super.mergeCode(userExercise, mainExercise, SPLITTER);
    }
}
