package dev.carlos.ItauDesafio.security;

import dev.carlos.ItauDesafio.entities.User;
import dev.carlos.ItauDesafio.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("ENTITY NOT FOUND"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getSenha(), new ArrayList<>());
    }
}
