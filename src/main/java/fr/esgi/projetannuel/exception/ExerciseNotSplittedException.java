package fr.esgi.projetannuel.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Slf4j
public class ExerciseNotSplittedException extends ApiBaseException{

    public ExerciseNotSplittedException(String context, String id) {
        super(context,id);
        log.error("An error occurred while splitting exercise to display : {}", id);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NO_CONTENT;
    }

    @Override
    public Map<String, Object> getErrors(){
        return errors;
    }
}
