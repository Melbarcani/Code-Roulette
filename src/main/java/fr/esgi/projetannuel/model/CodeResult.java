package fr.esgi.projetannuel.model;

import fr.esgi.projetannuel.enumeration.Status;
import lombok.Data;

@Data
public class CodeResult {
    private String outputConsole;
    private Status status;
    private String userId;

    public CodeResult(String outputConsole, Status status) {
        this.outputConsole = outputConsole;
        this.status = status;
        userId = null;
    }
}
