package com.codemasters.service;

import com.codemasters.model.Ferramenta;
import com.codemasters.repository.FerramentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FerramentasService {

    private final FerramentaRepository repository;

    public FerramentasService(FerramentaRepository repository) {
        this.repository = repository;
    }

    // Listar todas as ferramentas
    public List<Ferramenta> listar() {
        return repository.findAll();
    }

    // Buscar ferramenta pelo ID
    public Ferramenta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ferramenta não encontrada"));
    }

    // Cadastrar ferramenta
    public Ferramenta salvar(Ferramenta ferramenta) {
        return repository.save(ferramenta);
    }

    // Atualizar ferramenta
    public Ferramenta atualizar(Long id, Ferramenta dados) {

        Ferramenta ferramenta = buscarPorId(id);

        ferramenta.setNome(dados.getNome());
        ferramenta.setDescricao(dados.getDescricao());
        ferramenta.setQuantidade(dados.getQuantidade());
        ferramenta.setCategoria(dados.getCategoria());
        ferramenta.setStatus(dados.getStatus());

        return repository.save(ferramenta);
    }

    // Excluir ferramenta
    public void excluir(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Ferramenta não encontrada");
        }

        repository.deleteById(id);
    }
}