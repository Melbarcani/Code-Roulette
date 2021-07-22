package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Chat;
import fr.esgi.projetannuel.model.Dto.GameDto;
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
import java.util.*;

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

    public GameDto findDtoGameById(String id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game", id));
        return game.toDtoGame();
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

        // Retire users from Queue
        for(UserInGame userInGame: game.getUsersInGame()) {
            User user = userInGame.getUser();
            user.setInQueue(false);
            userRepository.save(user);

            usersInGameSaved.add(userInGameRepository.save(userInGame));
        }

        // randomly pick a UserIg for turn 1
        Random rand = new Random();
        int randomIndex = rand.nextInt(usersInGameSaved.size());
        UserInGame firstUserInGameToPlay = usersInGameSaved.get(randomIndex);

        // remove first UserInGame to play and shuffle the collection
        List<UserInGame> usersInGameShuffle = new ArrayList<>(usersInGameSaved);
        usersInGameShuffle.remove(firstUserInGameToPlay);
        Collections.shuffle(usersInGameShuffle, rand);

        // populate hashmap with first player and other players
        int order = 1;
        game.getTurnOrder().put(order, firstUserInGameToPlay.getId());
        for(UserInGame userIgRandom: usersInGameShuffle) {
            order++;
            game.getTurnOrder().put(order, userIgRandom.getId());
        }

        firstUserInGameToPlay.setCurrent(true);
        usersInGameSaved.set(randomIndex, userInGameRepository.save(firstUserInGameToPlay));

        game.setUsersInGame(usersInGameSaved);
        game.setChat(chatRepository.save(new Chat()));
        return gameRepository.save(game);
    }

    public Game endTurn(Game game) {
        // get current UserInGame
         UserInGame lastUserIgPlayed = game.getUsersInGame()
                .stream()
                .filter(UserInGame::isCurrent)
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("lastUserIgPlayed - Game's current User not found !", game.getId()));

        // increment turn + set current to false of UserInGame
        lastUserIgPlayed.incrementTurn();
        lastUserIgPlayed.setCurrent(false);
        lastUserIgPlayed = userInGameRepository.save(lastUserIgPlayed);

        // get key of last current User for turn order
        Integer key = getKeyByValue(game.getTurnOrder(), lastUserIgPlayed.getId());

        // if last reset or just increment
        if(key == null || key >= game.getUsersInGame().size()) {
            key = 0;
        }
        key++;

        // get next User with key
        Integer finalKey = key;
        UserInGame nextUserToPlay = game.getUsersInGame()
                .stream()
                .filter(userInGame -> userInGame.getId().equals(game.getTurnOrder().get(finalKey)))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("nextUserToPlay - Game's current User not found !", game.getId()));


        // Check if game is over
        if(nextUserToPlay.getTurn() >= game.getNumberOfTurn()) {
            game.setGameOver(true);

            UserInGame userScoreMax = game.getUsersInGame().get(0);

            // get user with max(score)
            for (UserInGame userInGameEnded: game.getUsersInGame()) {
                if (userInGameEnded.getScore() > userScoreMax.getScore()) {
                    userScoreMax = userInGameEnded;
                }
            }

            for (UserInGame userInGameEnded: game.getUsersInGame()) {
                if (!userInGameEnded.equals(userScoreMax)) {
                    userInGameEnded.getUser().decreaseElo(15);
                }
                userInGameEnded.getUser().increaseGamePlayed();
                userRepository.save(userInGameEnded.getUser());
            }

            userScoreMax.getUser().increaseGameWon();
            userScoreMax.getUser().increaseElo(15);
            userScoreMax.setUser(userRepository.save(userScoreMax.getUser()));
            userScoreMax.setWon(true);
            userInGameRepository.save(userScoreMax);

            return gameRepository.save(game);
        }

        // set next player to nextUserToPlay
        nextUserToPlay.setCurrent(true);
        userInGameRepository.save(nextUserToPlay);

        return game;
    }

    @Transactional
    public void deleteById(String id) {
        gameRepository.deleteById(id);
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
