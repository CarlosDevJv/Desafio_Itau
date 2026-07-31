package dev.carlos.ItauDesafio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_users")
public class User {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private String id;
    private String nome;
    private String email;
    private String senha;
    private Long numeroConta;
    private Double saldo;

    @OneToMany(mappedBy = "remetente")
    private Set<Transacao> transacaosRemetente = new HashSet<>();

    @OneToMany(mappedBy = "destinatario")
    private Set<Transacao> transacaosDestinatario = new HashSet<>();


    @Override
    public String toString(){
        return getNome() + ": " + saldo + "\n";
    }

}
