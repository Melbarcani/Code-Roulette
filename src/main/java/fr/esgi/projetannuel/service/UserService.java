package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return repository.findById(id);
    }

    public User create(User user) {
        return repository.save(user);
    }
}
