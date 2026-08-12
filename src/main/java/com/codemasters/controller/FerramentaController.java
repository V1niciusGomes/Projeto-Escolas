package com.codemasters.controller;

import com.codemasters.model.Ferramenta;
import com.codemasters.repository.FerramentaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
