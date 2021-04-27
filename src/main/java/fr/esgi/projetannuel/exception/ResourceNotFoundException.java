package fr.esgi.projetannuel.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ResourceNotFoundException extends RuntimeException {
    private final Map<String, Object> errors;

    public ResourceNotFoundException(String context, String id) {
        this.errors = new HashMap<>();
        this.errors.put("resource", context);
        this.errors.put("id", id);
        log.error("Resource {} with id {} not found", context, id);
    }

    public Map<String, Object> getErrors(){
        return errors;
    }
}
