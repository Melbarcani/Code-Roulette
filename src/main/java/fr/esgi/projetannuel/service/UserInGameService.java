package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.UserInGame;
import fr.esgi.projetannuel.repository.UserInGameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class UserInGameService {
    private final UserInGameRepository userInGameRepository;

    public List<UserInGame> findAll() {
        return userInGameRepository.findAll();
    }

    public UserInGame findById(String id) {
        return userInGameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UserInGame", id));
    }

    public UserInGame create(UserInGame userInGame) {
        return userInGameRepository.save(userInGame);
    }

    @Transactional
    public void deleteById(String id) {
        userInGameRepository.deleteById(id);
    }
}
