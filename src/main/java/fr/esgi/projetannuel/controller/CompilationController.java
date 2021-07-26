package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.enumeration.Status;
import fr.esgi.projetannuel.model.Compilation;
import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.model.Game;
import fr.esgi.projetannuel.model.NewCode;
import fr.esgi.projetannuel.repository.GameRepository;
import fr.esgi.projetannuel.repository.UserInGameRepository;
import fr.esgi.projetannuel.service.*;
import fr.esgi.projetannuel.service.code.NewCodeBuilderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Compilation>> findAll() {
        return new ResponseEntity<>(compilationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compilation> findById(@PathVariable String id) {
        return new ResponseEntity<>(compilationService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Compilation> save(@RequestBody String input) {
        return new ResponseEntity<>(compilationService.create(input), HttpStatus.CREATED);
    }

    @PostMapping("/compileAndSave/{timer}")
    public ResponseEntity<Compilation> compileAndSave(@PathVariable Long timer, @RequestBody Game game) {
        var userExercise = game.getExercise();
        String userId = sessionService.getCurrentUser().getId();
        String entireUserCode = compilationService.buildCodeToCompile(userExercise);
        var compilationResult = restService.postCode(entireUserCode, userExercise.getLanguage(), userExercise.getTitle(), userId);
        long score = computeScore(timer, game, compilationResult);

        Compilation compilation = compilationService.createCompilationDto(compilationResult, entireUserCode, userExercise, score);
        gameService.saveGameAfterCompilation(userExercise, game, compilationResult, compilation);

        return new ResponseEntity<>(compilation, HttpStatus.OK);
    }

    private long computeScore(Long timer, Game game, fr.esgi.projetannuel.model.CodeResult compilationResult) {
        return compilationResult.getStatus().equals(Status.SUCCESS)
                ? scoreService.computeScore(compilationResult, game, timer)
                : 0;
    }

    @PostMapping("/compileNewCode")
    public ResponseEntity<NewCode> compileNewCode(@RequestBody NewCode newCode) {
        var newCodeBuilder = NewCodeBuilderFactory.create(newCode);
        String userId = sessionService.getCurrentUser().getId();
        var compilationResult = restService.postCode(newCodeBuilder.execute(), newCode.getLanguage(), newCode.getTitle(), userId);
        newCode.setStatus(compilationResult.getStatus().toString());
        newCode.setCompilationOutput(compilationResult.getOutputConsole() + "\n" + String.join("\n", compilationResult.getRulesViolationList()));
        newCode.setCompilationScore(compilationResult.getInstructionsCount());
        newCode.setCodeViolationList(compilationResult.getRulesViolationList());
        return new ResponseEntity<>(newCode, HttpStatus.OK);
    }

    @PostMapping("/compileAndSaveExercise")//Check if this call exists in the front
    public ResponseEntity<Compilation> compileAndSave(@RequestBody Exercise userExercise/*, long spentTime*/) {
        String userId = sessionService.getCurrentUser().getId();
        String entireUserCode = compilationService.buildCodeToCompile(userExercise);
        var compilationResult = restService.postCode(entireUserCode, userExercise.getLanguage(), userExercise.getTitle(), userId);
        long score = scoreService.computeScore(userExercise, compilationResult.getInstructionsCount(), 1, 0);

        Compilation compilation = new Compilation(
                entireUserCode,
                compilationResult.getOutputConsole(),
                compilationResult.getStatus(),
                sessionService.getCurrentUser(),
                userExercise,
                score,
                0
        );

        compilationService.createFullCompilation(compilation);

        return new ResponseEntity<>(compilation, HttpStatus.OK);
    }

    /*@PostMapping("/compile")
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
