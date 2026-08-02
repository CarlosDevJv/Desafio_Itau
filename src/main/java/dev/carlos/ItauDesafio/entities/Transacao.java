package dev.carlos.ItauDesafio.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_transacao")
public class Transacao {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private String id;
    private Double valor;
    private OffsetDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "remetente")
    private User remetente;
    @ManyToOne
    @JoinColumn(name = "destinatario")
    private User destinatario;

    public Transacao (User remetente, User destinatario,  Double valor, OffsetDateTime dataHora){
        this.valor = valor;
        this.dataHora = dataHora;
        this.remetente = remetente;
        this.destinatario = destinatario;
    }
    public Transacao (Double valor){
        this.valor = valor;
    }


    @Override
    public String toString(){
        return "Valor : " + getValor() + " Data/Hora: " + getDataHora() + " Remetente: " + getRemetente() + " Destinatário: " + getDestinatario() +"\n";
    }


}
