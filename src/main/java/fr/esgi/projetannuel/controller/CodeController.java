package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.enumeration.Status;
import fr.esgi.projetannuel.model.Code;
import fr.esgi.projetannuel.service.CodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/code")
public class CodeController {
    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

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
        codeService.create(input);
        return new ResponseEntity<>(codeService.create(input), HttpStatus.CREATED);
    }

    @PostMapping("/compile")
    public ResponseEntity<Code> saveCompile(@RequestBody String input){
        String uri = "http://localhost:8099/api/compiler/java";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, input, String.class);

        String output = responseEntity.getBody();
        Status status = Status.ERROR;

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            status = Status.SUCCESS;
        }

        codeService.createWithOutput(input, output, status);

        return new ResponseEntity<>(codeService.createWithOutput(input, output, status), HttpStatus.CREATED);
    }

    @PostMapping("/compile/{id}")
    public ResponseEntity<Code> compileById(@PathVariable String id){
        Code code = codeService.findById(id);

        String uri = "http://localhost:8099/api/compiler/java";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, code.getInput(), String.class);

        String output = responseEntity.getBody();
        Status status = Status.ERROR;

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            status = Status.SUCCESS;
        }

        codeService.updateOutput(code, output, status);
        return new ResponseEntity<>(codeService.updateOutput(code, output, status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        codeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
