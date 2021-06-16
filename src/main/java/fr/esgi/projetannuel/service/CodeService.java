package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.enumeration.Status;
import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Code;
import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.repository.CodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CodeService {

    private final CodeRepository repository;
    private final CodeAdapterService codeAdapterService;

    public List<Code> findAll() {
        return repository.findAll();
    }

    public Code findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("code", id));
    }
    //mergeExoWithMain() and Compile()

    public Code create(String input) {
        Code code = new Code(input);
        return repository.save(code);
    }

    public Code createWithOutput(String input, String output, Status status) {
        Code code = new Code(input, output, status);
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

    /*public String buildCodeToCompile(Exercise exercise) {
        var codeAdapter = codeAdapterService.create(exercise.getLanguage());
        String entireUserCode = codeAdapter.mergeCode(exercise);
    }*/
}
