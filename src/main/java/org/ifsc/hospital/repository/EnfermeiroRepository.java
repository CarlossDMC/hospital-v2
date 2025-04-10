package org.ifsc.hospital.repository;

import org.ifsc.hospital.model.Enfermeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnfermeiroRepository extends 
        JpaRepository<Enfermeiro, Long>,
        JpaSpecificationExecutor<Enfermeiro> {
}