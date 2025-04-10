package org.ifsc.hospital.controller;

import jakarta.persistence.criteria.Path;
import org.ifsc.hospital.model.Acompanhante;
import org.ifsc.hospital.repository.AcompanhanteRepository;
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
@RequestMapping("/acompanhante")
public class AcompanhanteController {

    @Autowired
    private AcompanhanteRepository acompanhanteRepository;

    @PostMapping
    public ResponseEntity<Acompanhante> criarAcompanhante(@RequestBody Acompanhante acompanhante) {
        Acompanhante novoAcompanhante = acompanhanteRepository.save(acompanhante);
        return ResponseEntity.status(201).body(novoAcompanhante);
    }

    @GetMapping
    public List<Acompanhante> listarAcompanhantes(@RequestParam Map<String, String> filters) {
        Specification<Acompanhante> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            filters.forEach((campo, valor) -> {
                if (valor != null && !valor.isEmpty()) {
                    try {
                        Path<String> fieldPath = root.get(campo);
                        predicates.add((Predicate) cb.like(fieldPath, "%" + valor + "%"));
                    } catch (IllegalArgumentException e) {
                    }
                }
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return acompanhanteRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acompanhante> buscarPorId(@PathVariable Long id) {
        Optional<Acompanhante> acompanhante = acompanhanteRepository.findById(id);
        return acompanhante.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Acompanhante> atualizarAcompanhante(
            @PathVariable Long id,
            @RequestBody Acompanhante acompanhanteAtualizado) {

        return acompanhanteRepository.findById(id)
                .map(existente -> {
                    BeanUtils.copyProperties(acompanhanteAtualizado, existente, "id");
                    return ResponseEntity.ok(acompanhanteRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAcompanhante(@PathVariable Long id) {
        try {
            acompanhanteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

}