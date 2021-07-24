package fr.esgi.projetannuel.service.code;

import fr.esgi.projetannuel.model.NewCode;
import lombok.RequiredArgsConstructor;

import static fr.esgi.projetannuel.service.code.JavaCodeWrapper.*;

@RequiredArgsConstructor
public class NewJavaCodeBuilder implements NewCodeBuilder{
    private final NewCode newCode;

    public String execute(){
        var entireCode = new StringBuilder();
        entireCode.append(CLASS_MAIN_BUILDER.replace(CLASS_NAME, newCode.getTitle().trim()))
                .append(INNER_CLASS)
                .append(String.join("\n", newCode.getTests()))
                .append(RETURN_OUTPUT)
                .append(METHOD_MODIFIER_VISIBILITY)
                .append(newCode.getCode())
                .append(CLOSE_BUILDING);
        return entireCode.toString();
    }
}
