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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Acompanhante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fone1;
    private String fone2;
    private String cpfCnpj;
    private String rgInscricaoEstadual;
    private LocalDateTime dataCadastro;
    private String cep;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String complemento;
    private String nome;
    private String grauParentesco;
    private String cpf;
    private String fone;
    private String email;
    private String status;

}
