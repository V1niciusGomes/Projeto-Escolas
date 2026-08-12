package com.codemasters.service;

import com.codemasters.model.Emprestimo;
import com.codemasters.model.Ferramenta;
import com.codemasters.model.Funcionario;
import com.codemasters.repository.EmprestimoRepository;
import com.codemasters.repository.FerramentaRepository;
import com.codemasters.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private FerramentaRepository ferramentaRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    private Ferramenta ferramenta;
    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        ferramenta = new Ferramenta();
        ferramenta.setId(1L);

        funcionario = new Funcionario();
        funcionario.setId(1L);
    }

    @Test
    void isDisponivel_whenNoActiveLoans_returnsTrue() {
        when(ferramentaRepository.findById(1L)).thenReturn(Optional.of(ferramenta));
        when(emprestimoRepository.findByFerramentaAndDataDevolucaoIsNull(ferramenta)).thenReturn(List.of());

        boolean disponivel = emprestimoService.isDisponivel(1L);

        assertThat(disponivel).isTrue();
    }

    @Test
    void isDisponivel_whenThereIsActiveLoan_returnsFalse() {
        when(ferramentaRepository.findById(1L)).thenReturn(Optional.of(ferramenta));
        Emprestimo e = new Emprestimo();
        e.setId(1L);
        when(emprestimoRepository.findByFerramentaAndDataDevolucaoIsNull(ferramenta)).thenReturn(List.of(e));

        boolean disponivel = emprestimoService.isDisponivel(1L);

        assertThat(disponivel).isFalse();
    }

    @Test
    void registrarEmprestimo_success() {
        when(ferramentaRepository.findById(1L)).thenReturn(Optional.of(ferramenta));
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(emprestimoRepository.findByFerramentaAndDataDevolucaoIsNull(ferramenta)).thenReturn(List.of());

        Emprestimo saved = new Emprestimo();
        saved.setId(10L);
        when(emprestimoRepository.save(any())).thenReturn(saved);

        Emprestimo result = emprestimoService.registrarEmprestimo(1L, 1L);

        assertThat(result.getId()).isEqualTo(10L);
        verify(emprestimoRepository, times(1)).save(any());
    }

    @Test
    void registrarEmprestimo_whenUnavailable_throws() {
        when(ferramentaRepository.findById(1L)).thenReturn(Optional.of(ferramenta));
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        Emprestimo e = new Emprestimo();
        when(emprestimoRepository.findByFerramentaAndDataDevolucaoIsNull(ferramenta)).thenReturn(List.of(e));

        assertThrows(IllegalStateException.class, () -> emprestimoService.registrarEmprestimo(1L, 1L));
    }

    @Test
    void registrarDevolucao_success() {
        Emprestimo e = new Emprestimo();
        e.setId(5L);
        e.setDataEmprestimo(LocalDateTime.now());
        when(emprestimoRepository.findById(5L)).thenReturn(Optional.of(e));
        when(emprestimoRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Emprestimo result = emprestimoService.registrarDevolucao(5L);

        assertThat(result.getDataDevolucao()).isNotNull();
        verify(emprestimoRepository).save(e);
    }

    @Test
    void registrarDevolucao_whenAlreadyReturned_throws() {
        Emprestimo e = new Emprestimo();
        e.setId(6L);
        e.setDataEmprestimo(LocalDateTime.now());
        e.setDataDevolucao(LocalDateTime.now());
        when(emprestimoRepository.findById(6L)).thenReturn(Optional.of(e));

        assertThrows(IllegalStateException.class, () -> emprestimoService.registrarDevolucao(6L));
    }
}
