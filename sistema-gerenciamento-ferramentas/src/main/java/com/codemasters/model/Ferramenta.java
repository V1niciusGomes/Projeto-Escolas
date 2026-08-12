package com.codemasters.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ferramentas")
public class Ferramenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    private Integer quantidade;

    private String categoria;

    private String status;

    public Ferramenta() {
    }

    public Ferramenta(String nome, String descricao, Integer quantidade,
                      String categoria, String status) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}