package dev.carlos.ItauDesafio.entities.dto.response;

import java.time.OffsetDateTime;

public record TransacaoResponse(Double valor, OffsetDateTime offsetDateTime) {
}
