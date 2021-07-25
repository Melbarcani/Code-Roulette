package fr.esgi.projetannuel.service.code;


import fr.esgi.projetannuel.model.NewCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
                .append(createIndentedTests(newCode.getTests()))
                .append("\n")
                .append(FOOTER);
        return entireCode.toString();
    }

    private String createIndentedTests(String[] tests) {
        String str ="";
        for (String test : tests) {
            String[] lines = test.split("\n");
            for (String line : lines) {
                str += "\n"+"\t"+line;
            }
        }
        return str;
    }
}
