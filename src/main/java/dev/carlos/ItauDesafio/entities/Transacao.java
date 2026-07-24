package dev.carlos.ItauDesafio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Transacao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Double valor;
    private OffsetDateTime dataHora;

    public Transacao (Double valor, OffsetDateTime dataHora){
        this.valor = valor;
        this.dataHora = dataHora;
    }
    public Transacao (Double valor){
        this.valor = valor;
    }


    @Override
    public String toString(){
        return "Valor : " + getValor() + " Data/Hora: " + getDataHora() + "\n";
    }


}
