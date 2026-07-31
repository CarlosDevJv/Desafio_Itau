package dev.carlos.ItauDesafio.repository;

import dev.carlos.ItauDesafio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNumeroConta(Long numeroConta);

    Optional<User> findByEmail(String username);
}
