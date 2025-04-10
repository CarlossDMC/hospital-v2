package org.ifsc.hospital.controller;

import jakarta.persistence.criteria.Path;
import org.ifsc.hospital.model.Enfermeiro;
import org.ifsc.hospital.repository.EnfermeiroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;

@RestController
@RequestMapping("/enfermeiro")
public class EnfermeiroController {

    @Autowired
    private EnfermeiroRepository enfermeiroRepository;

    @PostMapping
    public ResponseEntity<Enfermeiro> criarEnfermeiro(@RequestBody Enfermeiro enfermeiro) {
        Enfermeiro novoEnfermeiro = enfermeiroRepository.save(enfermeiro);
        return ResponseEntity.status(201).body(novoEnfermeiro);
    }

    @GetMapping
    public List<Enfermeiro> listarEnfermeiros(@RequestParam Map<String, String> filters) {
        Specification<Enfermeiro> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            filters.forEach((campo, valor) -> {
                if (valor != null && !valor.isEmpty()) {
                    try {
                        Path<String> fieldPath = root.get(campo);
                        predicates.add(cb.like(fieldPath, "%" + valor + "%"));
                    } catch (IllegalArgumentException e) {
                    }
                }
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return enfermeiroRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enfermeiro> buscarPorId(@PathVariable Long id) {
        Optional<Enfermeiro> enfermeiro = enfermeiroRepository.findById(id);
        return enfermeiro.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enfermeiro> atualizarEnfermeiro(
            @PathVariable Long id,
            @RequestBody Enfermeiro enfermeiroAtualizado) {

        return enfermeiroRepository.findById(id)
                .map(existente -> {
                    BeanUtils.copyProperties(enfermeiroAtualizado, existente, "id");
                    return ResponseEntity.ok(enfermeiroRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEnfermeiro(@PathVariable Long id) {
        try {
            enfermeiroRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}