package fr.esgi.projetannuel.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ApiBaseException.class)
    protected ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getErrors(), new HttpHeaders(), exception.getStatusCode(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException exception, WebRequest request){
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "Could not perform SQL operation");
            errors.put("error", "Constraint Violation");

            return handleExceptionInternal(exception, errors, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
