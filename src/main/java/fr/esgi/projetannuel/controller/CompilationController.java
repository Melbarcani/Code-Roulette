package fr.esgi.projetannuel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.esgi.projetannuel.enumeration.Status;
import fr.esgi.projetannuel.model.*;
import fr.esgi.projetannuel.repository.GameRepository;
import fr.esgi.projetannuel.repository.UserInGameRepository;
import fr.esgi.projetannuel.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;
    private final GameService gameService;
    private final GameRepository gameRepository;
    private final RestService restService;
    private final ExerciseService exerciseService;
    private final SessionService sessionService;
    private final ScoreService scoreService;
    private final UserInGameService userInGameService;
    private final UserInGameRepository userInGameRepository;


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

    @PostMapping("/compileAndSave/{timer}")
    public ResponseEntity<Compilation> compileAndSave(@PathVariable Long timer, @RequestBody Game game) { //@RequestBody Game game, Long timer){
        Exercise userExercise = game.getExercise();
        String userId = sessionService.getCurrentUser().getId();
        String entireUserCode = compilationService.buildCodeToCompile(userExercise);

        var compilationResult = restService.postCode(entireUserCode, userExercise.getLanguage(), userExercise.getTitle(), userId);
        long score = scoreService.computeScore(userExercise, compilationResult.getInstructionsCount(), (game.getTimer() - timer));

        Compilation compilation = new Compilation(
                entireUserCode,
                compilationResult.getOutputConsole(),
                compilationResult.getStatus(),
                sessionService.getCurrentUser(),
                userExercise,
                score
        );

        compilationService.createFullCompilation(compilation);

        // game.setCode(compilation.getInput());
        game.getCompilations().add(compilation);
        gameRepository.save(game);

        return new ResponseEntity<>(compilation, HttpStatus.OK);
    }

    @PostMapping("/compileAndSaveExercise")
    public ResponseEntity<Compilation> compileAndSave(@RequestBody Exercise userExercise/*, long spentTime*/){
        String userId = sessionService.getCurrentUser().getId();
        String entireUserCode = compilationService.buildCodeToCompile(userExercise);
        var compilationResult = restService.postCode(entireUserCode, userExercise.getLanguage(), userExercise.getTitle(), userId);
        long score = scoreService.computeScore(userExercise, compilationResult.getInstructionsCount(), 0);

        System.out.println(score);

        Compilation compilation = new Compilation(
                entireUserCode,
                compilationResult.getOutputConsole(),
                compilationResult.getStatus(),
                sessionService.getCurrentUser(),
                userExercise,
                score
        );

        compilationService.createFullCompilation(compilation);

        return new ResponseEntity<>(compilation, HttpStatus.OK);
    }

/*    @PostMapping("/compile")
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
    }*/
}
