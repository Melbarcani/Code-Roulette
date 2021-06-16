package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController
{
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        exerciseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
