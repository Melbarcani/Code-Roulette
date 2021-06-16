package fr.esgi.projetannuel.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class ApiBaseException extends RuntimeException{
    protected final Map<String, Object> errors;

    ApiBaseException(String context, String id) {
        this.errors = new HashMap<>();
        this.errors.put("resource", context);
        this.errors.put("id", id);
    }

    public abstract HttpStatus getStatusCode();

    public Map<String, Object> getErrors(){
        return errors;
    }
}
