package org.ifsc.hospital.controller;

import jakarta.persistence.criteria.Path;
import org.ifsc.hospital.model.Farmaceutico;
import org.ifsc.hospital.repository.FarmaceuticoRepository;
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
@RequestMapping("/farmaceutico")
public class FarmaceuticoController {

    @Autowired
    private FarmaceuticoRepository farmaceuticoRepository;

    @PostMapping
    public ResponseEntity<Farmaceutico> criarFarmaceutico(@RequestBody Farmaceutico farmaceutico) {
        Farmaceutico novoFarmaceutico = farmaceuticoRepository.save(farmaceutico);
        return ResponseEntity.status(201).body(novoFarmaceutico);
    }

    @GetMapping
    public List<Farmaceutico> listarFarmaceuticos(@RequestParam Map<String, String> filters) {
        Specification<Farmaceutico> spec = (root, query, cb) -> {
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

        return farmaceuticoRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Farmaceutico> buscarPorId(@PathVariable Long id) {
        Optional<Farmaceutico> farmaceutico = farmaceuticoRepository.findById(id);
        return farmaceutico.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Farmaceutico> atualizarFarmaceutico(
            @PathVariable Long id,
            @RequestBody Farmaceutico farmaceuticoAtualizado) {

        return farmaceuticoRepository.findById(id)
                .map(existente -> {
                    BeanUtils.copyProperties(farmaceuticoAtualizado, existente, "id");
                    return ResponseEntity.ok(farmaceuticoRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFarmaceutico(@PathVariable Long id) {
        try {
            farmaceuticoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}