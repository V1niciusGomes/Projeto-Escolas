package com.codemasters.service;

import com.codemasters.model.Emprestimo;
import com.codemasters.model.Ferramenta;
import com.codemasters.model.Funcionario;
import com.codemasters.repository.EmprestimoRepository;
import com.codemasters.repository.FerramentaRepository;
import com.codemasters.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final FerramentaRepository ferramentaRepository;
    private final FuncionarioRepository funcionarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             FerramentaRepository ferramentaRepository,
                             FuncionarioRepository funcionarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.ferramentaRepository = ferramentaRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public boolean isDisponivel(Long ferramentaId) {
        Optional<Ferramenta> fOpt = ferramentaRepository.findById(ferramentaId);
        if (fOpt.isEmpty()) return false;
        Ferramenta f = fOpt.get();
        // verifica se há empréstimos ativos
        return emprestimoRepository.findByFerramentaAndDataDevolucaoIsNull(f).isEmpty();
    }

    @Transactional
    public Emprestimo registrarEmprestimo(Long ferramentaId, Long funcionarioId) {
        Ferramenta f = ferramentaRepository.findById(ferramentaId)
                .orElseThrow(() -> new IllegalArgumentException("Ferramenta não encontrada"));
        Funcionario func = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (!isDisponivel(ferramentaId)) {
            throw new IllegalStateException("Ferramenta indisponível no momento");
        }

        Emprestimo e = new Emprestimo();
        e.setFerramenta(f);
        e.setFuncionario(func);
        e.setDataEmprestimo(LocalDateTime.now());
        return emprestimoRepository.save(e);
    }

    @Transactional
    public Emprestimo registrarDevolucao(Long emprestimoId) {
        Emprestimo e = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));
        if (e.getDataDevolucao() != null) {
            throw new IllegalStateException("Empréstimo já devolvido");
        }
        e.setDataDevolucao(LocalDateTime.now());
        return emprestimoRepository.save(e);
    }

    public List<Emprestimo> consultarHistoricoPorFerramenta(Long ferramentaId) {
        return emprestimoRepository.findByFerramentaIdOrderByDataEmprestimoDesc(ferramentaId);
    }

    public List<Emprestimo> consultarHistoricoPorFuncionario(Long funcionarioId) {
        return emprestimoRepository.findByFuncionarioIdOrderByDataEmprestimoDesc(funcionarioId);
    }
}
