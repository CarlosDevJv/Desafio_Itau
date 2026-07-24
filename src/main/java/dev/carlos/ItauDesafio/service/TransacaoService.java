package dev.carlos.ItauDesafio.service;

import dev.carlos.ItauDesafio.entities.Transacao;
import dev.carlos.ItauDesafio.entities.dto.request.TransacaoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    List<Transacao> transacaoList = new ArrayList<>();
    List<Double> transacoesRecentes = new ArrayList<>();

    public HttpStatus criarTransacao(TransacaoRequest request){
        if (request.valor() < 0){
            throw new IllegalArgumentException("O VALOR DA TRANSFERÊNCIA NÃO PODE SER MENOR QUE ZERO");
        }
        Transacao transacao = new Transacao(request.valor(), OffsetDateTime.now());
        arqurivar(transacao);
        return HttpStatus.CREATED;
    }

    public void arqurivar(Transacao transacao){
        transacaoList.add(transacao);
    }

    public void listaTransacoes(){
        System.out.println(transacaoList);
    }

    public List<Double> transacoesRecentes(){
        OffsetDateTime now = OffsetDateTime.now();
        transacoesRecentes = transacaoList.stream()
                .filter(transacao -> transacao.getDataHora().isAfter(now.minusSeconds(60))
                && transacao.getDataHora().isBefore(now.plusSeconds(1)))
                .map(Transacao::getValor)
                .toList();
        return transacoesRecentes;
    }

    public void estatisticas(){
        DoubleSummaryStatistics summaryStatistics = transacoesRecentes.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();
        System.out.println("Quantidade de transações nos últimos 60 segundos: " + summaryStatistics.getCount());
        System.out.println("Soma total do valor transacionado nos últimos 60 segundos: " + summaryStatistics.getSum());
        System.out.println("Média do valor transacionado nos últimos 60 segundos: " + summaryStatistics.getAverage());
        System.out.println("Maior valor transacionado nos últimos 60 segundos: " + summaryStatistics.getMax());
        System.out.println("Menor valor transacionado nos últimos 60 segundos: " + summaryStatistics.getMin());
    }
}
