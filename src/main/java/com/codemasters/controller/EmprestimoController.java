package com.codemasters.controller;

import com.codemasters.model.Emprestimo;
import com.codemasters.service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping("/disponivel/{ferramentaId}")
    public ResponseEntity<Map<String, Boolean>> verificarDisponibilidade(@PathVariable Long ferramentaId) {
        boolean disponivel = emprestimoService.isDisponivel(ferramentaId);
        return ResponseEntity.ok(Map.of("disponivel", disponivel));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarEmprestimo(@RequestBody Map<String, Long> body) {
        Long ferramentaId = body.get("ferramentaId");
        Long funcionarioId = body.get("funcionarioId");
        Emprestimo e = emprestimoService.registrarEmprestimo(ferramentaId, funcionarioId);
        return ResponseEntity.ok(e);
    }

    @PostMapping("/devolucao/{id}")
    public ResponseEntity<?> registrarDevolucao(@PathVariable Long id) {
        Emprestimo e = emprestimoService.registrarDevolucao(id);
        return ResponseEntity.ok(e);
    }

    @GetMapping("/historico/ferramenta/{ferramentaId}")
    public ResponseEntity<List<Emprestimo>> historicoFerramenta(@PathVariable Long ferramentaId) {
        return ResponseEntity.ok(emprestimoService.consultarHistoricoPorFerramenta(ferramentaId));
    }

    @GetMapping("/historico/funcionario/{funcionarioId}")
    public ResponseEntity<List<Emprestimo>> historicoFuncionario(@PathVariable Long funcionarioId) {
        return ResponseEntity.ok(emprestimoService.consultarHistoricoPorFuncionario(funcionarioId));
    }
}
