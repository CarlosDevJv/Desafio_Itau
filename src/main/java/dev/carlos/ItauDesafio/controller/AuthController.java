package dev.carlos.ItauDesafio.controller;

import dev.carlos.ItauDesafio.entities.User;
import dev.carlos.ItauDesafio.entities.dto.request.UserLoginRequest;
import dev.carlos.ItauDesafio.entities.dto.request.UserRegisterRequest;
import dev.carlos.ItauDesafio.entities.dto.response.UserAccessResponse;
import dev.carlos.ItauDesafio.entities.dto.response.UserLoginResponse;
import dev.carlos.ItauDesafio.repository.UserRepository;
import dev.carlos.ItauDesafio.security.TokenProvider;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest){
        var authToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha());
        Authentication authentication = authenticationManager.authenticate(authToken);

        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND"));

        String jwt = tokenProvider.generateToken(user);

        return ResponseEntity.ok(new UserLoginResponse(user.getEmail(), user.getNumeroConta(), jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest registerRequest){
        Optional<User> user = userRepository.findByEmail(registerRequest.email());

        if (user.isEmpty()){
            User newUser = new User();
            newUser.setEmail(registerRequest.email());
            newUser.setNome(registerRequest.nome());
            newUser.setSaldo(1500.0);
            newUser.setNumeroConta(User.gerarSenha11Digitos()); //Teste
            newUser.setSenha(passwordEncoder.encode(registerRequest.senha()));
            userRepository.save(newUser);

            String token = tokenProvider.generateToken(newUser);


            return ResponseEntity.ok(new UserAccessResponse(registerRequest.nome(), token, newUser.getNumeroConta()));
        }
        return ResponseEntity.badRequest().build();
    }
}
