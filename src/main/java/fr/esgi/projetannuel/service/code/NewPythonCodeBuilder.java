package fr.esgi.projetannuel.service.code;


import fr.esgi.projetannuel.model.NewCode;
import lombok.RequiredArgsConstructor;

import static fr.esgi.projetannuel.service.code.PythonCodeWrapper.*;

@RequiredArgsConstructor
public class NewPythonCodeBuilder implements NewCodeBuilder {

    private final NewCode newCode;

    @Override
    public String execute() {
        var entireCode = new StringBuilder();
        entireCode.append(HEADER)
                .append(newCode.getCode())
                .append(BODY)
                .append(String.join("\n\t",newCode.getTests()))
                .append("\n")
                .append(FOOTER);
        return entireCode.toString();
    }
}
