package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Chat;
import fr.esgi.projetannuel.model.Game;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.ChatRepository;
import fr.esgi.projetannuel.repository.GameRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findById(String id) {
        return gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game", id));
    }

    public List<Game> findByUserId(String userId) {
        List<Game> gamesOfUser = new ArrayList<>();

        for (Game game: gameRepository.findAll()) {
            for(User user: game.getUsers()) {
                if(user.getId().equals(userId)){
                    gamesOfUser.add(game);
                }
            }
        }

        return gamesOfUser;
    }

    public Game create(Game game) {
        for( User userIg : game.getUsers()){
            userIg.setInQueue(false);
            userRepository.save(userIg);
        }
        game.setChat(chatRepository.save(new Chat()));
        return gameRepository.save(game);
    }

    @Transactional
    public void deleteById(String id) {
        gameRepository.deleteById(id);
    }
}
