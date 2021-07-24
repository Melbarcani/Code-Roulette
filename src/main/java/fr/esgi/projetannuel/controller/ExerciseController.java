package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.model.NewCode;
import fr.esgi.projetannuel.service.code.NewCodeBuilder;
import fr.esgi.projetannuel.service.ExerciseService;
import fr.esgi.projetannuel.service.code.NewCodeBuilderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController
{
    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<Exercise>> findAll(){
        return new ResponseEntity<>(exerciseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> findById(@PathVariable String id){
        return new ResponseEntity<>(exerciseService.getExerciseToDisplay(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Exercise> save(@RequestBody Exercise exercise){
        return new ResponseEntity<>(exerciseService.create(exercise), HttpStatus.CREATED);
    }

    @PostMapping("/save")
    public ResponseEntity<NewCode> save(@RequestBody NewCode newCode){
        NewCodeBuilder codeBuilder = NewCodeBuilderFactory.create(newCode);
        var exercise = new Exercise(newCode.getTitle(), codeBuilder.execute(), newCode.getDescription(), newCode.getLanguage());
        exerciseService.create(exercise);
        return new ResponseEntity<>(newCode, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        exerciseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/random")
    public ResponseEntity<Exercise> getRandom(){
        List<Exercise> exercises = exerciseService.findAll();
        Exercise exercise;

        if(exercises.size() == 1) {
            exercise = exercises.get(0);
            return new ResponseEntity<>(exercise, HttpStatus.OK);
        }

        Random rand = new Random();
        exercise = exercises.get(rand.nextInt(exercises.size() - 1));

        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }
}
