package fr.esgi.projetannuel.controller;

import fr.esgi.projetannuel.service.JavaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/java")
@AllArgsConstructor
public class JavaController {

    private final JavaService javaService;

    @GetMapping("/exo/{id}")
    public ResponseEntity<String> getExo(@PathVariable String id) {
        return new ResponseEntity<>(javaService.getExo(id), HttpStatus.OK);
    }

    @PostMapping("/exo/{id}")
    public ResponseEntity<String> compileExo(@PathVariable String id, @RequestBody String exo) throws IOException, ClassNotFoundException {
        return new ResponseEntity(javaService.compileExoFile(id,exo), HttpStatus.OK);
    }
}
