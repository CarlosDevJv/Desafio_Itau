package dev.carlos.ItauDesafio.controller;

import dev.carlos.ItauDesafio.entities.Transacao;
import dev.carlos.ItauDesafio.entities.dto.request.TransacaoRequest;
import dev.carlos.ItauDesafio.entities.dto.response.TransacaoResponse;
import dev.carlos.ItauDesafio.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/banco")
public class TransacaoController {
    @Autowired
    TransacaoService transacaoService;

    @PostMapping("/transacao")
    public ResponseEntity<?> criarTransacao(@RequestBody TransacaoRequest transacaoRequest){
        try {
            transacaoService.criarTransacao(transacaoRequest);
        } catch (IllegalArgumentException e){
            return ResponseEntity.unprocessableContent().build();
        }
        return ResponseEntity.ok("Transação Concluída" + new TransacaoResponse(transacaoRequest.valor(), OffsetDateTime.now()));
    }

    @GetMapping("/estatistica")
    public ResponseEntity<?> estatisticas(){
        transacaoService.estatisticas();
        return ResponseEntity.ok().build();
    }
}
