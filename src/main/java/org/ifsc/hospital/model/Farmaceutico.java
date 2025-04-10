package org.ifsc.hospital.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Farmaceutico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String fone1;
    private String fone2;
    private String email;
    private String cpfCnpj;
    private String rgInscricaoEstadual;
    private LocalDateTime dataCadastro;
    private String cep;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String complemento;
    private String cfr;
    private String senha;
    private String login;
    private String nomeSocial;
}
