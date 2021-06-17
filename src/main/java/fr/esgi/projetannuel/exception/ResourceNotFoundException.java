package fr.esgi.projetannuel.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Slf4j
public class ResourceNotFoundException extends ApiBaseException {

    public ResourceNotFoundException(String context, String id) {
        super(context, id);
        log.error("Resource {} with id {} not found", context, id);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

    public Map<String, Object> getErrors(){
        return errors;
    }
}
