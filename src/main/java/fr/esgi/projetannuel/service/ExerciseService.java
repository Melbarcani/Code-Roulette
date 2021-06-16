package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.enumeration.Language;
import fr.esgi.projetannuel.exception.ExerciseNotSplittedException;
import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository repository;
    private final JavaAdapterService javaService;

    public List<Exercise> findAll() {
        return repository.findAll();
    }

    public Exercise getExercise(String id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exercise", id));
    }

    public Exercise getExerciseToDisplay(String id) { //codeService pour split
        var exercise = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exercise", id));
        var codeToDisplay = "";
        if(Language.Java.equals(exercise.getLanguage())){
            codeToDisplay = javaService.createExerciseToDisplay(exercise.getId(),exercise.getCode());
        } else if (Language.Python.equals(exercise.getLanguage())){
            codeToDisplay = "";
        }
        if(codeToDisplay.isBlank()){
            throw new ExerciseNotSplittedException("exercise", id);
        }

        return new Exercise(exercise.getTitle(), codeToDisplay, exercise.getLanguage());
    }

    /*creer createExoToDisplay()
            mergeExoWithMain() and Compile()*/

    public Exercise create(Exercise exercise) {
        return repository.save(exercise);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
