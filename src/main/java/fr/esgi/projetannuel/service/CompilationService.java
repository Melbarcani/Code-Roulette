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

    private final CompilationRepository compilationRepository;
    private final ExerciseService exerciseService;
    private final UserService userService;

    public List<Compilation> findAll() {
        return compilationRepository.findAll();
    }

    public Compilation findById(String id) {
        return compilationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("code", id));
    }

    public Compilation create(String input) {
        var code = new Compilation(input);
        return compilationRepository.save(code);
    }

    public Compilation createFullCompilation(Compilation compilation) {
        userService.updateCompilationCompleted();
        return compilationRepository.save(compilation);
    }

    public Compilation createWithOutput(String input, String output, Status status) {
        var code = new Compilation(input, output, status);
        return compilationRepository.save(code);
    }

    public Compilation updateOutput(Compilation compilation, String output, Status status) {
        compilation.setStatus(status);
        compilation.setOutput(output);
        compilation.setCompiledAt(LocalDateTime.now());

        return compilationRepository.save(compilation);
    }

    @Transactional
    public void deleteById(String id) {
        compilationRepository.deleteById(id);
    }

    public String buildCodeToCompile(Exercise userExercise) {
        var codeAdapter = CodeAdapterServiceFactory.create(userExercise.getLanguage());
        var mainExercise = exerciseService.getExercise(userExercise.getId());
        return codeAdapter.mergeCode(userExercise, mainExercise);
    }
}
