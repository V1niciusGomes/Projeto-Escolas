package com.codemasters.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Emprestimo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Ferramenta ferramenta;

    @ManyToOne(optional = false)
    private Funcionario funcionario;

    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucao; // null enquanto emprestado

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Ferramenta getFerramenta() { return ferramenta; }
    public void setFerramenta(Ferramenta ferramenta) { this.ferramenta = ferramenta; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public LocalDateTime getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDateTime dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDateTime getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDateTime dataDevolucao) { this.dataDevolucao = dataDevolucao; }
}
