package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.exception.UserAlreadyInLobbyException;
import fr.esgi.projetannuel.model.Lobby;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.LobbyRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LobbyService {
    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;

    private final SessionService sessionService;

    public List<Lobby> findAll() {
        return lobbyRepository.findAll();
    }

    public Lobby findById(String id) {
        return lobbyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("lobby", id));
    }

    public User create(String userId, String lobbyTitle) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()) {
            User userPresent = user.get();
            Lobby lobby = new Lobby(lobbyTitle);
            lobby.getUsers().add(userPresent);
            lobbyRepository.save(lobby);

            userPresent.setLobbyId(lobby.getId());
            return userRepository.save(userPresent);
        }

        throw new UserAlreadyInLobbyException(userId);
    }

    @Transactional
    public void deleteById(String id) {
        lobbyRepository.deleteById(id);
    }

    @Transactional
    public User joinLobby(String lobbyId) {
        User userConnected = sessionService.getCurrentUser();
        userConnected.setLobbyId(lobbyId);

        Lobby lobby = findById(lobbyId);
        lobby.getUsers().add(userConnected);
        lobbyRepository.save(lobby);

        return userRepository.save(userConnected);
    }

    @Transactional
    public void leaveLobby(String lobbyId) {
        User userConnected = sessionService.getCurrentUser();
        Lobby lobby = findById(lobbyId);

        if(lobby != null) {
            lobby.getUsers().remove(userConnected);

            if(lobby.getUsers().size() == 0){
                deleteById(lobbyId);
            }else {
                lobbyRepository.save(lobby);
            }
        }

        userConnected.setLobbyId(null);
        userRepository.save(userConnected);
    }
}
