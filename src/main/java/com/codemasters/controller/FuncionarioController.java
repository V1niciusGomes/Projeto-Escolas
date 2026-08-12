package com.codemasters.controller;

import com.codemasters.model.Funcionario;
import com.codemasters.repository.FuncionarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository repository;

    public FuncionarioController(FuncionarioRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Funcionario> cadastrar(@RequestBody Funcionario funcionario) {
        Funcionario salvo = repository.save(funcionario);
        return ResponseEntity.ok(salvo);
    }

    // você pode adicionar GET/PUT/DELETE conforme necessidade
}
