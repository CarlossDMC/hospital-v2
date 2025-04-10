package org.ifsc.hospital.repository;

import org.ifsc.hospital.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FornecedorRepository extends 
        JpaRepository<Fornecedor, Long>,
        JpaSpecificationExecutor<Fornecedor> {
}