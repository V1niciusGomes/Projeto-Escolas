package com.codemasters.repository;

import com.codemasters.model.Emprestimo;
import com.codemasters.model.Ferramenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    // empréstimos ativos para uma ferramenta (dataDevolucao == null)
    List<Emprestimo> findByFerramentaAndDataDevolucaoIsNull(Ferramenta ferramenta);

    List<Emprestimo> findByFerramentaIdOrderByDataEmprestimoDesc(Long ferramentaId);
    List<Emprestimo> findByFuncionarioIdOrderByDataEmprestimoDesc(Long funcionarioId);
}
