package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Chat;
import fr.esgi.projetannuel.model.Game;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.model.UserInGame;
import fr.esgi.projetannuel.repository.ChatRepository;
import fr.esgi.projetannuel.repository.GameRepository;
import fr.esgi.projetannuel.repository.UserInGameRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ExerciseService exerciseService;
    private final UserInGameRepository userInGameRepository;
    private final ChatRepository chatRepository;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findById(String id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game", id));
        game.setExercise(exerciseService.getExerciseToDisplay(game.getExercise().getId()));

        return game;
    }

    public List<Game> findByUserId(String userId) {
        List<Game> gamesOfUser = new ArrayList<>();

        for (Game game: gameRepository.findAll()) {
            for (UserInGame userInGame: game.getUsersInGame()) {
                if(userInGame.getUser().getId().equals(userId)){
                    gamesOfUser.add(game);
                }
            }
        }

        return gamesOfUser;
    }

    public Game create(Game game) {
        List<UserInGame> usersInGameSaved = new ArrayList<>();

        for(UserInGame userInGame: game.getUsersInGame()) {
            User user = userInGame.getUser();
            user.setInQueue(false);
            userRepository.save(user);

            usersInGameSaved.add(userInGameRepository.save(userInGame));
        }
        game.setUsersInGame(usersInGameSaved);
        game.setChat(chatRepository.save(new Chat()));
        return gameRepository.save(game);
    }

    @Transactional
    public void deleteById(String id) {
        gameRepository.deleteById(id);
    }
}
