package fr.esgi.projetannuel.service.code;

import fr.esgi.projetannuel.model.NewCode;
import org.springframework.stereotype.Service;

@Service
public class NewCodeBuilderFactory {
    private NewCodeBuilderFactory(){}

    public static NewCodeBuilder create(NewCode newCode){
        switch (newCode.getLanguage()){
            case Java :
                return new NewJavaCodeBuilder(newCode);
            case Python:
                return new NewPythonCodeBuilder(newCode);
            default:
                return null;
        }
    }
}
