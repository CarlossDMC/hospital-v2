package org.ifsc.hospital.repository;

import org.ifsc.hospital.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PacienteRepository extends 
        JpaRepository<Paciente, Long>, 
        JpaSpecificationExecutor<Paciente> {
}