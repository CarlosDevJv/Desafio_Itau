package dev.carlos.ItauDesafio.repository;

import dev.carlos.ItauDesafio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
