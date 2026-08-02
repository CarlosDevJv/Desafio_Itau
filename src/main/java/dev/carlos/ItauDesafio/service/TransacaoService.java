package dev.carlos.ItauDesafio.service;

import dev.carlos.ItauDesafio.entities.Transacao;
import dev.carlos.ItauDesafio.entities.User;
import dev.carlos.ItauDesafio.entities.dto.request.TransacaoRequest;
import dev.carlos.ItauDesafio.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class TransacaoService {
    @Autowired
    UserRepository userRepository;

    List<Transacao> transacaoList = new ArrayList<>();
    List<Double> transacoesRecentes = new ArrayList<>();

    public void criarTransacao(TransacaoRequest request){
        User remetente;
        User destinatario;

        if (!request.emailRemetente().isEmpty() && !request.emailDestinatario().isEmpty()){
            remetente = userRepository.findByEmail(request.emailRemetente()).orElseThrow(() -> new EntityNotFoundException("Remetente não encontrado"));
            destinatario = userRepository.findByEmail(request.emailDestinatario()).orElseThrow(() -> new EntityNotFoundException("Destinatário não encontrado"));
            if ((request.valor() < 0) || (request.valor() > remetente.getSaldo())){
                throw new IllegalArgumentException("VALOR INSUFICIENTE");
            }
            remetente.setSaldo(remetente.getSaldo() - request.valor());
            destinatario.setSaldo(destinatario.getSaldo() + request.valor());
            userRepository.save(remetente);
            userRepository.save(destinatario);
            Transacao transacao = new Transacao(remetente, destinatario,request.valor(), OffsetDateTime.now());
            arqurivar(transacao);
        }
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
