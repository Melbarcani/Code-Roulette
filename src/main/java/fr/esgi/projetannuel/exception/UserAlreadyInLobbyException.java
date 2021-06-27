package fr.esgi.projetannuel.exception;

import fr.esgi.projetannuel.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserAlreadyInLobbyException extends RuntimeException {
    private final Map<User, Object> errors;

    public UserAlreadyInLobbyException(String userId) {
        this.errors = new HashMap<>();
        log.error("User {} is already in a lobby", userId);
    }

    public Map<User, Object> getErrors(){
        return errors;
    }
}
