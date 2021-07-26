package fr.esgi.projetannuel.model;

import fr.esgi.projetannuel.enumeration.Status;
import lombok.Data;

import java.util.List;

@Data
public class CodeResult {
    private String outputConsole;
    private Status status;
    private String userId;
    private long instructionsCount;
    private List<String> rulesViolationList;

    public CodeResult() {};

    public CodeResult(String outputConsole, Status status, long instructionsCount,
                      List<String> rulesViolationList) {
        this.outputConsole = outputConsole;
        this.status = status;
        this.instructionsCount = instructionsCount;
        this.rulesViolationList = rulesViolationList;
        userId = null;
    }
}
