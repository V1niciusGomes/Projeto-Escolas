package com.codemasters.controller;

import com.codemasters.model.Ferramenta;
import com.codemasters.service.FerramentasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ferramentas")
@CrossOrigin(origins = "*")
public class FerramentaController {

    private final FerramentasService service;

    public FerramentaController(FerramentasService service) {
        this.service = service;
    }

    // GET /ferramentas
    @GetMapping
    public ResponseEntity<List<Ferramenta>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /ferramentas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Ferramenta> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // POST /ferramentas
    @PostMapping
    public ResponseEntity<Ferramenta> cadastrar(
            @RequestBody Ferramenta ferramenta) {

        Ferramenta novaFerramenta = service.salvar(ferramenta);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novaFerramenta);
    }

    // PUT /ferramentas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Ferramenta> atualizar(
            @PathVariable Long id,
            @RequestBody Ferramenta ferramenta) {

        return ResponseEntity.ok(
                service.atualizar(id, ferramenta)
        );
    }

    // DELETE /ferramentas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}