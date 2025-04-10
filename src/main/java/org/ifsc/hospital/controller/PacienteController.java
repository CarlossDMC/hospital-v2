// PacienteController.java
package org.ifsc.hospital.controller;

import org.ifsc.hospital.model.Paciente;
import org.ifsc.hospital.repository.PacienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<Paciente> criarPaciente(@RequestBody Paciente paciente) {
        Paciente novoPaciente = pacienteRepository.save(paciente);
        return ResponseEntity.status(201).body(novoPaciente);
    }

    @GetMapping
    public List<Paciente> listarPacientes(@RequestParam Map<String, String> filters) {
        Specification<Paciente> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            filters.forEach((campo, valor) -> {
                if (valor != null && !valor.isEmpty()) {
                    try {
                        Path<String> fieldPath = root.get(campo);
                        predicates.add(cb.like(fieldPath, "%" + valor + "%"));
                    } catch (IllegalArgumentException e) {
                        // Ignora campos não existentes na entidade
                    }
                }
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return pacienteRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizarPaciente(
            @PathVariable Long id,
            @RequestBody Paciente pacienteAtualizado) {

        return pacienteRepository.findById(id)
                .map(existente -> {
                    BeanUtils.copyProperties(pacienteAtualizado, existente, "id");
                    return ResponseEntity.ok(pacienteRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        try {
            pacienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}