package fr.esgi.projetannuel.model;

import fr.esgi.projetannuel.enumeration.Status;
import lombok.Data;

@Data
public class CodeResult {
    private String outputConsole;
    private Status status;
    private String userId;
    private long instructionsCount;

    public CodeResult() {};

    public CodeResult(String outputConsole, Status status, long instructionsCount) {
        this.outputConsole = outputConsole;
        this.status = status;
        this.instructionsCount = instructionsCount;
        userId = null;
    }
}
