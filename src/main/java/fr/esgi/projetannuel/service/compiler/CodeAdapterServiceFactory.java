package fr.esgi.projetannuel.service.compiler;

import fr.esgi.projetannuel.enumeration.Language;
import org.springframework.stereotype.Service;

@Service
public class CodeAdapterServiceFactory {
    private CodeAdapterServiceFactory() {
    }

    public static AbstractCompilerService create(Language language){
        switch (language){
            case Java :
                return new JavaAdapterService();
            case Python:
                return new PythonAdapterService();
            default:
                return new CAdapterSerice();
        }
    }
}
