package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Constants;
import fr.esgi.projetannuel.model.Queue;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.QueueRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;
    private final UserRepository userRepository;

    private final SessionService sessionService;

    public List<Queue> findAll() {
        return queueRepository.findAll();
    }

    public Queue findById(String id) {
        return queueRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.USER, id));
    }

    @Transactional
    public List<User> joinQueue() {
        User userConnected = sessionService.getCurrentUser();
        List<User> users = userRepository.findAll();

        Optional<User> userInQueue = users.stream()
                .filter(userStream -> userStream.getQueue() != null)
                .findFirst();

        if(userInQueue.isPresent()) {
            List<User> usersMatched = new ArrayList<>();
            usersMatched.add(userConnected);
            usersMatched.add(userInQueue.get());

            return usersMatched;
        }

        Queue queue = new Queue(LocalDateTime.now());
        queueRepository.save(queue);
        userConnected.setQueue(queue);
        userRepository.save(userConnected);

        return Collections.emptyList();
    }

    @Transactional
    public void leaveQueue() {
        User user = sessionService.getCurrentUser();
        queueRepository.deleteById(user.getQueue().getId());
        user.setQueue(null);
        userRepository.save(user);
    }
}
