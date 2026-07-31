package dev.carlos.ItauDesafio.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import dev.carlos.ItauDesafio.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenProvider {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration}")
    private long expiration;

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("ItauDesafio")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException jwtCreationException){
                throw new RuntimeException();
        }
    }

    public String validToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ItauDesafio")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException jwtCreationException){
            throw  new RuntimeException();
        }
    }


    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusMinutes(15)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
