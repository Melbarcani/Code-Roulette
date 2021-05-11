package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.repository.InMemoryCode;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class JavaService {

    private final InMemoryCode inMemoryCode;
    private String[] parts;
    private static final String SPLITTER = "//&&&&&&&";

    public String getExo(String exoId) {
        String exoPath = inMemoryCode.getExoPath(exoId);
        String content = null;
        try {
            content = readFile(exoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createExerciseToDisplay(exoId, content);
    }

    private String createExerciseToDisplay(String exoId, String content) {
        if (content != null) {
            parts = content.split(SPLITTER);
        }
        if(parts.length > 0){
            return parts[1];
        }
        throw new ResourceNotFoundException("exo", exoId);
    }

    public String compileExoFile(String exoId, String userCode) throws IOException, ClassNotFoundException {
        String code = addUserCode(userCode);
        var tempFile = inMemoryCode.createTempUserCodeFile(code, exoId);

        OutputStream output = createOutputErrorFile();

        var compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, output, tempFile.getPath());
        var error = output.toString();

        var classLoader = URLClassLoader.newInstance(new URL[]{inMemoryCode.getUserRoot().toURI().toURL()});
        Class.forName(inMemoryCode.getUserExoFileName(exoId), true, classLoader);

        return error.isBlank() ? "Success" : "Failed \n" + error;
    }

    private OutputStream createOutputErrorFile() {
        return new OutputStream() {
            private final StringBuilder sb = new StringBuilder();

            @Override
            public void write(int b) {
                sb.append((char) b);
            }

            @Override
            public String toString() {
                return sb.toString();
            }
        };
    }


    private String addUserCode(String userCode) {
        if(parts.length>1)
            return parts[0] + SPLITTER + userCode + SPLITTER + parts[2];
        return "";
    }

    private static String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path), StandardCharsets.UTF_8);
    }


}
