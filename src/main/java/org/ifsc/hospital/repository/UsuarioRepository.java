package org.ifsc.hospital.repository;

import org.ifsc.hospital.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsuarioRepository extends 
        JpaRepository<Usuario, Long>, 
        JpaSpecificationExecutor<Usuario> {
}