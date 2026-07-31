package dev.carlos.ItauDesafio.entities.dto.response;

public record UserLoginResponse(String email, Long numeroConta, String token) {
}
