package dev.carlos.ItauDesafio.entities.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record TransacaoRequest(String emailRemetente, String emailDestinatario, @NotNull Double valor) {
}
