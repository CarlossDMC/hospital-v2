package org.ifsc.hospital.controller;

import org.ifsc.hospital.model.Usuario;
import org.ifsc.hospital.repository.UsuarioRepository;
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
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(201).body(novoUsuario);
    }

    @GetMapping
    public List<Usuario> listarUsuarios(@RequestParam Map<String, String> filters) {
        Specification<Usuario> spec = (root, query, cb) -> {
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

        return usuarioRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuarioAtualizado) {

        return usuarioRepository.findById(id)
                .map(existente -> {
                    BeanUtils.copyProperties(usuarioAtualizado, existente, "id");
                    return ResponseEntity.ok(usuarioRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}