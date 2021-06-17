package fr.esgi.projetannuel.service.compiler;

import fr.esgi.projetannuel.model.Exercise;

public interface ICompilerService {

    String SPLITTER = "//&&&&&&&";

    String mergeCode(Exercise userExercise, Exercise mainExercise );
    String createExerciseToDisplay(String exoId, String content);

}
