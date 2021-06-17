package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.enumeration.Status;
import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Code;
import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.repository.CodeRepository;
import fr.esgi.projetannuel.service.compiler.CodeAdapterServiceFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CodeService {

    private final CodeRepository repository;
    private final ExerciseService exerciseService;

    public List<Code> findAll() {
        return repository.findAll();
    }

    public Code findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("code", id));
    }

    public Code create(String input) {
        var code = new Code(input);
        return repository.save(code);
    }

    public Code createWithOutput(String input, String output, Status status) {
        var code = new Code(input, output, status);
        return repository.save(code);
    }

    public Code updateOutput(Code code, String output, Status status) {
        code.setStatus(status);
        code.setOutput(output);
        code.setCompiledAt(LocalDateTime.now());

        return repository.save(code);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public String buildCodeToCompile(Exercise userExercise) {
        var codeAdapter = CodeAdapterServiceFactory.create(userExercise.getLanguage());
        var mainExercise = exerciseService.getExercise(userExercise.getId());
        return codeAdapter.mergeCode(userExercise, mainExercise);
    }
}
