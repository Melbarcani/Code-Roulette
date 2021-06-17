package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.enumeration.Status;
import fr.esgi.projetannuel.model.Code;
import fr.esgi.projetannuel.model.CodeResult;
import fr.esgi.projetannuel.model.Constants;
import fr.esgi.projetannuel.service.CodeService;
import fr.esgi.projetannuel.service.ExerciseService;
import fr.esgi.projetannuel.service.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;
    private final RestService restService;
    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<Code>> findAll(){
        return new ResponseEntity<>(codeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Code> findById(@PathVariable String id){
        return new ResponseEntity<>(codeService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Code> save(@RequestBody String input){
        return new ResponseEntity<>(codeService.create(input), HttpStatus.CREATED);
    }

    @PostMapping("/compileAndSave")
    public ResponseEntity<CodeResult> compileAndSave(@RequestBody String userCode){ // Should be exercise in the bodyRequest
        /** Bootstrap to test code **/
        var userExercise = exerciseService.getExercise("c43ef6bc-4727-46f7-a616-ded75c70dedc");
        /****************************/

        String entireUserCode = codeService.buildCodeToCompile(userExercise);
        var compilationResult = restService.postCode(entireUserCode, userExercise.getLanguage());
        System.out.println(compilationResult);
        return new ResponseEntity<>(compilationResult, HttpStatus.OK);
    }

    @PostMapping("/compile")
    public ResponseEntity<Code> saveCompiledCode(@RequestBody String input){
        var restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constants.COMPILER_BASE_URL, input, String.class);

        //restService.someRestCall(input);

        String output = responseEntity.getBody();
        Status status = Status.ERROR;

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            status = Status.SUCCESS;
        }

        return new ResponseEntity<>(codeService.createWithOutput(input, output, status), HttpStatus.CREATED);
    }

    @PostMapping("/compile/{id}")
    public ResponseEntity<Code> compileById(@PathVariable String id){
        Code code = codeService.findById(id);

        String uri = "http://localhost:9099/api/compiler/java";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, code.getInput(), String.class);

        String output = responseEntity.getBody();
        Status status = Status.ERROR;

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            status = Status.SUCCESS;
        }

        return new ResponseEntity<>(codeService.updateOutput(code, output, status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        codeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
