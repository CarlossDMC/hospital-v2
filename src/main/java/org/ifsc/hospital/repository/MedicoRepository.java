package org.ifsc.hospital.repository;

import org.ifsc.hospital.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MedicoRepository extends 
        JpaRepository<Medico, Long>, 
        JpaSpecificationExecutor<Medico> {
}