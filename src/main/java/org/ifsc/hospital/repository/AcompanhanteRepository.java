package org.ifsc.hospital.repository;

import org.ifsc.hospital.model.Acompanhante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AcompanhanteRepository extends
        JpaRepository<Acompanhante, Long>,
        JpaSpecificationExecutor<Acompanhante> {
}