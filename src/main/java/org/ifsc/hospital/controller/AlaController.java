package org.ifsc.hospital.controller;

import jakarta.persistence.criteria.Path;
import org.ifsc.hospital.model.Ala;
import org.ifsc.hospital.repository.AlaRepository;
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
@RequestMapping("/ala")
public class AlaController {

    @Autowired
    private AlaRepository alaRepository;

    @PostMapping
    public ResponseEntity<Ala> criarAla(@RequestBody Ala ala) {
        Ala novaAla = alaRepository.save(ala);
        return ResponseEntity.status(201).body(novaAla);
    }

    @GetMapping
    public List<Ala> listarAlas(@RequestParam Map<String, String> filters) {
        Specification<Ala> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            filters.forEach((campo, valor) -> {
                if (valor != null && !valor.isEmpty()) {
                    try {
                        Path<String> fieldPath = root.get(campo);
                        predicates.add(cb.like(fieldPath, "%" + valor + "%"));
                    } catch (IllegalArgumentException e) {
                        // Ignora campos não existentes
                    }
                }
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return alaRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ala> buscarPorId(@PathVariable Integer id) {
        Optional<Ala> ala = alaRepository.findById(id);
        return ala.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ala> atualizarAla(
            @PathVariable Integer id,
            @RequestBody Ala alaAtualizada) {

        return alaRepository.findById(id)
                .map(existente -> {
                    BeanUtils.copyProperties(alaAtualizada, existente, "id");
                    return ResponseEntity.ok(alaRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAla(@PathVariable Integer id) {
        try {
            alaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}   