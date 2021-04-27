package fr.esgi.projetannuel.repository;

import fr.esgi.projetannuel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    void deleteById(String id);
}
