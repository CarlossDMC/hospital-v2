package org.ifsc.hospital.controller;

import org.ifsc.hospital.model.Medico;
import org.ifsc.hospital.repository.MedicoRepository;
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
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<Medico> criarMedico(@RequestBody Medico medico) {
        Medico novoMedico = medicoRepository.save(medico);
        return ResponseEntity.status(201).body(novoMedico);
    }

    @GetMapping
    public List<Medico> listarMedicos(@RequestParam Map<String, String> filters) {
        Specification<Medico> spec = (root, query, cb) -> {
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

        return medicoRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscarPorId(@PathVariable Long id) {
        Optional<Medico> medico = medicoRepository.findById(id);
        return medico.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> atualizarMedico(
            @PathVariable Long id,
            @RequestBody Medico medicoAtualizado) {

        return medicoRepository.findById(id)
                .map(existente -> {
                    BeanUtils.copyProperties(medicoAtualizado, existente, "id");
                    return ResponseEntity.ok(medicoRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMedico(@PathVariable Long id) {
        try {
            medicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}