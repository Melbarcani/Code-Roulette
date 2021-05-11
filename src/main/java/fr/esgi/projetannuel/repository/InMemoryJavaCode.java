package fr.esgi.projetannuel.repository;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

@Repository
public class InMemoryJavaCode implements InMemoryCode {

    private static final String ROOT = "/java/";
    private static final String ROOT_USER = ROOT + "user/";
    private static final String EXO = "Exo";
    private static final String JAVA_EXTENSION = ".java";

    @Override
    public String getExoPath(String id) {
        return ROOT + EXO + id + JAVA_EXTENSION;
    }
    public File getUserRoot() {
        return new File(ROOT_USER);
    }

    @Override
    public File createTempUserCodeFile(String code, String exoId) throws IOException {
        String userPath = ROOT_USER + EXO  + exoId + JAVA_EXTENSION;
        var userFile = new File(userPath);
        Files.write(Path.of(userFile.getPath()), Collections.singleton(code));
        return new File(userPath);
    }

    @Override
    public String getUserExoFileName(String exoId) {
        return EXO + exoId;
    }
}
