package fr.esgi.projetannuel.service.code;


import fr.esgi.projetannuel.model.NewCode;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                .append(String.join("\n",createIndentedTests(newCode.getTests())))
                .append("\n")
                .append(FOOTER);
        return entireCode.toString();
    }

    private List<String> createIndentedTests(String[] tests){
        return Arrays.stream(tests).map(test -> "\t" +test).collect(Collectors.toList());
    }
}
