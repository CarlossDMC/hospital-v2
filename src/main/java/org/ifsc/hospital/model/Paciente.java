package org.ifsc.hospital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Paciente {
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
    private String tipoSanguineo;
    private String sexo;
    private String nomeSocial;
    private LocalDate dataNascimento;


}
