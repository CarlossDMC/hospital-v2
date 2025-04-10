package org.ifsc.hospital.controller;

import jakarta.persistence.criteria.Path;

import org.ifsc.hospital.dto.QuartoDTO;
import org.ifsc.hospital.model.Ala;
import org.ifsc.hospital.model.Quarto;
import org.ifsc.hospital.repository.AlaRepository;
import org.ifsc.hospital.repository.QuartoRepository;
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
@RequestMapping("/quarto")
public class QuartoController {

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private AlaRepository alaRepository;


    @PostMapping
    public ResponseEntity<Quarto> criarQuarto(@RequestBody QuartoDTO quarto) {
        Ala ala = alaRepository.findById(quarto.getAlaId()).orElse(null);


        Quarto quartoEntity = new Quarto();
        quartoEntity.setAla(ala);
        quartoEntity.setDescricao(quarto.getDescricao());
        quartoEntity.setStatus(quarto.getStatus());

        Quarto novoQuarto = quartoRepository.save(quartoEntity);
        return ResponseEntity.status(201).body(novoQuarto);
    }

    @GetMapping
    public List<Quarto> listarQuartos(@RequestParam Map<String, String> filters) {
        Specification<Quarto> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            filters.forEach((campo, valor) -> {
                if (valor != null && !valor.isEmpty()) {
                    try {
                        // Tratamento especial para relacionamento com Ala
                        if (campo.equals("ala")) {
                            Path<Object> alaPath = root.get("ala").get("id");
                            predicates.add(cb.equal(alaPath, valor));
                        } else {
                            Path<String> fieldPath = root.get(campo);
                            predicates.add(cb.like(fieldPath, "%" + valor + "%"));
                        }
                    } catch (IllegalArgumentException e) {
                        // Ignora campos não existentes
                    }
                }
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return quartoRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscarPorId(@PathVariable Long id) {
        Optional<Quarto> quarto = quartoRepository.findById(id);
        return quarto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quarto> atualizarQuarto(
            @PathVariable Long id,
            @RequestBody Quarto quartoAtualizado) {

        return quartoRepository.findById(id)
                .map(existente -> {
                    BeanUtils.copyProperties(quartoAtualizado, existente, "id", "ala");
                    // Mantém o relacionamento ala se não for especificado na atualização
                    if (quartoAtualizado.getAla() != null) {
                        existente.setAla(quartoAtualizado.getAla());
                    }
                    return ResponseEntity.ok(quartoRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarQuarto(@PathVariable Long id) {
        try {
            quartoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}