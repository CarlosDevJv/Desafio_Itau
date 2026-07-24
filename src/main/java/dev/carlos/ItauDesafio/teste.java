package dev.carlos.ItauDesafio;

import dev.carlos.ItauDesafio.entities.Transacao;
import dev.carlos.ItauDesafio.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Scanner;
@Component
public class teste {
    public static void main(String[] args) {
        TransacaoService transacaoService = new TransacaoService();
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 2; i++) {
            System.out.println("Valor da transação: ");
            Double valor = scanner.nextDouble();
            OffsetDateTime dateTime = OffsetDateTime.now().minusSeconds(30);
            Transacao transacao = new Transacao(valor, dateTime);
            transacaoService.arqurivar(transacao);
            transacaoService.listaTransacoes();
        }

    }
}
