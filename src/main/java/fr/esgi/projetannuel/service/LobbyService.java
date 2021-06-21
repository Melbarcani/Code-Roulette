package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Lobby;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.LobbyRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    public Lobby create(Lobby lobby) {
        return lobbyRepository.save(lobby);
    }

    @Transactional
    public void deleteById(String id) {
        lobbyRepository.deleteById(id);
    }

    @Transactional
    public void joinLobby(String lobbyId) {
        User userConnected = sessionService.getCurrentUser();
        Lobby lobby = findById(lobbyId);
        userConnected.setLobby(lobby);
        userRepository.save(userConnected);
    }

    @Transactional
    public void leaveLobby(String lobbyId) {
        User userConnected = sessionService.getCurrentUser();
        Lobby lobby = findById(lobbyId);
        userConnected.setLobby(null);
        userRepository.save(userConnected);
    }

    public List<User> lobbyUsers(String lobbyId) {
        return userRepository.findAll()
                .stream()
                .filter(userStream -> userStream.getLobby().getId().equals(lobbyId))
                .collect(Collectors.toList());
    }
}
