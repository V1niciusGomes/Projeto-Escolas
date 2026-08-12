package com.codemasters.controller;

import com.codemasters.model.Emprestimo;
import com.codemasters.service.EmprestimoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmprestimoController.class)
class EmprestimoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmprestimoService emprestimoService;

    @Test
    void verificarDisponibilidade_returnsJson() throws Exception {
        given(emprestimoService.isDisponivel(1L)).willReturn(true);

        mockMvc.perform(get("/api/emprestimos/disponivel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.disponivel").value(true));
    }

    @Test
    void registrarEmprestimo_returnsEmprestimo() throws Exception {
        Emprestimo e = new Emprestimo();
        e.setId(100L);
        e.setDataEmprestimo(LocalDateTime.now());

        given(emprestimoService.registrarEmprestimo(eq(1L), eq(1L))).willReturn(e);

        Map<String, Long> body = Map.of("ferramentaId", 1L, "funcionarioId", 1L);

        mockMvc.perform(post("/api/emprestimos/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    void registrarDevolucao_returnsEmprestimo() throws Exception {
        Emprestimo e = new Emprestimo();
        e.setId(10L);
        e.setDataEmprestimo(LocalDateTime.now());
        e.setDataDevolucao(LocalDateTime.now());

        given(emprestimoService.registrarDevolucao(10L)).willReturn(e);

        mockMvc.perform(post("/api/emprestimos/devolucao/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.dataDevolucao").exists());
    }

    @Test
    void historicoFerramenta_returnsList() throws Exception {
        Emprestimo e = new Emprestimo();
        e.setId(1L);
        given(emprestimoService.consultarHistoricoPorFerramenta(1L)).willReturn(List.of(e));

        mockMvc.perform(get("/api/emprestimos/historico/ferramenta/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
