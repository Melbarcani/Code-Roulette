package fr.esgi.projetannuel.repository;

import java.io.File;
import java.io.IOException;

public interface InMemoryCode {
    String getExoPath(String Id);

    File createTempUserCodeFile(String code, String exoId) throws IOException;

    String getUserExoFileName(String exoId);

    File getUserRoot();
}
