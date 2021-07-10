package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Constants;
import fr.esgi.projetannuel.model.Session;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.SessionRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    private final SessionService sessionService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.USER, id));
    }

    public Session findByToken(String token) {
        return sessionRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("session", token));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    public User updateElo(User user, int elo) {
        user.setElo(user.getElo() + elo);
        return userRepository.save(user);
    }

    @Transactional
    public List<User> joinQueue() {
        User userConnected = sessionService.getCurrentUser();
        List<User> users = findAll();

        Optional<User> userMatched = users
                .stream()
                .filter(User::getInQueue)
                .findFirst();


        if(userMatched.isPresent()) {
            List<User> usersMatched = new ArrayList<>();
            usersMatched.add(userConnected);
            usersMatched.add(userMatched.get());

            return usersMatched;
        }

        userConnected.setInQueue(true);
        userRepository.save(userConnected);

        return Collections.emptyList();
    }

    @Transactional
    public void leaveQueue() {
        User user = sessionService.getCurrentUser();
        user.setInQueue(false);
        userRepository.save(user);
    }

    public Long usersInQueue() {
        return userRepository.findAll()
                .stream()
                .filter(User::getInQueue)
                .count();
    }

    public User updateUser(User user) {
        var updatedUser = userRepository.findById(user.getId()).orElseThrow(() -> {throw new ResourceNotFoundException("update email", user.getId());});
        updatedUser = user;
        return userRepository.save(updatedUser);
    }

    public void updateCompilationCompleted() {
        User user = sessionService.getCurrentUser();
        user.setCompilationCompleted(user.getCompilationCompleted()+1);
        userRepository.save(user);
    }
}
