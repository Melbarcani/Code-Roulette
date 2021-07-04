package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.enumeration.Status;
import fr.esgi.projetannuel.model.Compilation;
import fr.esgi.projetannuel.model.CodeResult;
import fr.esgi.projetannuel.model.Constants;
import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;
    private final RestService restService;
    private final ExerciseService exerciseService;
    private final SessionService sessionService;
    private final ScoreService scoreService;

    @GetMapping
    public ResponseEntity<List<Compilation>> findAll(){
        return new ResponseEntity<>(compilationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compilation> findById(@PathVariable String id){
        return new ResponseEntity<>(compilationService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Compilation> save(@RequestBody String input){
        return new ResponseEntity<>(compilationService.create(input), HttpStatus.CREATED);
    }

    @PostMapping("/compileAndSave")
    public ResponseEntity<Compilation> compileAndSave(@RequestBody Exercise userExercise/*, long spentTime*/){
        String userId = sessionService.getCurrentUser().getId();
        String entireUserCode = compilationService.buildCodeToCompile(userExercise);
        var compilationResult = restService.postCode(entireUserCode, userExercise.getLanguage(), userExercise.getTitle(), userId);
        long score = scoreService.computeScore(userExercise.getInitialInstructionsCount(), compilationResult.getInstructionsCount()/*, time*/);

        System.out.println(score);

        Compilation compilation = new Compilation(
                entireUserCode,
                compilationResult.getOutputConsole(),
                compilationResult.getStatus(),
                sessionService.getCurrentUser(),
                userExercise
        );

        compilationService.createFullCompilation(compilation);

        return new ResponseEntity<>(compilation, HttpStatus.OK);
    }

    @PostMapping("/compile")
    public ResponseEntity<Compilation> saveCompiledCode(@RequestBody String input){
        var restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constants.COMPILER_BASE_URL, input, String.class);

        //restService.someRestCall(input);

        String output = responseEntity.getBody();
        Status status = Status.ERROR;

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            status = Status.SUCCESS;
        }

        return new ResponseEntity<>(compilationService.createWithOutput(input, output, status), HttpStatus.CREATED);
    }

    @PostMapping("/compile/{id}")
    public ResponseEntity<Compilation> compileById(@PathVariable String id){
        Compilation compilation = compilationService.findById(id);

        String uri = Constants.COMPILER_BASE_URL;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, compilation.getInput(), String.class);

        String output = responseEntity.getBody();
        Status status = Status.ERROR;

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            status = Status.SUCCESS;
        }

        return new ResponseEntity<>(compilationService.updateOutput(compilation, output, status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        compilationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
