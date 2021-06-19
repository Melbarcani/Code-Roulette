package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.enumeration.Status;
import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Compilation;
import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.repository.CompilationRepository;
import fr.esgi.projetannuel.service.compiler.CodeAdapterServiceFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CompilationService {

    private final CompilationRepository repository;
    private final ExerciseService exerciseService;

    public List<Compilation> findAll() {
        return repository.findAll();
    }

    public Compilation findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("code", id));
    }

    public Compilation create(String input) {
        var code = new Compilation(input);
        return repository.save(code);
    }

    public Compilation createFullCompilation(Compilation compilation) {
        return repository.save(compilation);
    }

    public Compilation createWithOutput(String input, String output, Status status) {
        var code = new Compilation(input, output, status);
        return repository.save(code);
    }

    public Compilation updateOutput(Compilation compilation, String output, Status status) {
        compilation.setStatus(status);
        compilation.setOutput(output);
        compilation.setCompiledAt(LocalDateTime.now());

        return repository.save(compilation);
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
