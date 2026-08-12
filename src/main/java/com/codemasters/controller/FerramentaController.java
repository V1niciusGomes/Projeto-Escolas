package com.codemasters.controller;

import com.codemasters.model.Ferramenta;
import com.codemasters.repository.FerramentaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/ferramentas")
public class FerramentaController {

    private final FerramentaRepository repository;

    public FerramentaController(FerramentaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Ferramenta>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Ferramenta> criar(@RequestBody Ferramenta ferramenta) {
        Ferramenta salvo = repository.save(ferramenta);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ferramenta> atualizar(@PathVariable Long id, @RequestBody Ferramenta body) {
        Ferramenta existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ferramenta não encontrada"));
        // update fields (adjust according to your Ferramenta fields)
        existing.setNome(body.getNome());
        existing.setDescricao(body.getDescricao());
        Ferramenta saved = repository.save(existing);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ferramenta não encontrada");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
