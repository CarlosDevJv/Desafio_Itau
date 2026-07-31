package dev.carlos.ItauDesafio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_users")
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }


    private static final SecureRandom random = new SecureRandom();

    public static long gerarSenha11Digitos() {
        long min = 10000000000L; // Menor número de 11 dígitos
        long max = 99999999999L; // Maior número de 11 dígitos

        // Gera um número entre min e max (inclusivo)
        return min + (long)(random.nextDouble() * (max - min + 1));
    }

}
