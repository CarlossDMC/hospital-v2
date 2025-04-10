package org.ifsc.hospital.repository;

import org.ifsc.hospital.model.Ala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlaRepository extends 
        JpaRepository<Ala, Long>,
        JpaSpecificationExecutor<Ala> {
}