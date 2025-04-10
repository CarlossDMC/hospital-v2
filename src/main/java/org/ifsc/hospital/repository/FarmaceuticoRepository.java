package org.ifsc.hospital.repository;

import org.ifsc.hospital.model.Farmaceutico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FarmaceuticoRepository extends 
        JpaRepository<Farmaceutico, Long>,
        JpaSpecificationExecutor<Farmaceutico> {
}