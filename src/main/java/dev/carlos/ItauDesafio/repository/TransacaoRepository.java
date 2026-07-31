package dev.carlos.ItauDesafio.repository;

import dev.carlos.ItauDesafio.entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, String> {
}
