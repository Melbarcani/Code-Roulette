package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.enumeration.Language;
import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Exercise;
import fr.esgi.projetannuel.repository.InMemoryCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class
JavaAdapterService implements ICompilerService {

    //private final ExerciseService exerciseService;

    private String[] parts;
    private static String SPLITTER = "//&&&&&&&";

    public Exercise getExo(String exoId) {
        return null;//exerciseService.getExercise(exoId);
    }

    public String createExerciseToDisplay(String exoId, String content) {
        if (content != null) {
            parts = content.split(SPLITTER);
        }
        if(parts.length > 0){
            return parts[1];
        }
        throw new ResourceNotFoundException("exo", exoId);
    }


    private String addUserCode(String userCode) {
        if(parts.length>1)
            return parts[0] + SPLITTER + userCode + SPLITTER + parts[2];
        return "";
    }

    private static String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path), StandardCharsets.UTF_8);
    }


    @Override
    public String mergeCode(Exercise userExercise) {
        String userCode = userExercise.getCode();
        String mainCode = getExo(userExercise.getId()).getCode();

        if (userCode != null) {
            parts = userCode.split(SPLITTER);
        }
        if(parts.length > 0){
            return parts[1];
        }
        throw new ResourceNotFoundException("exo", userExercise.getId());
    }
}
