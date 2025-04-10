package org.ifsc.hospital.repository;

import org.ifsc.hospital.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuartoRepository extends 
        JpaRepository<Quarto, Long>,
        JpaSpecificationExecutor<Quarto> {
}