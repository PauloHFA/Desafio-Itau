package com.example.demo.itau.testecontroller;

import com.example.demo.itau.controller.TransacaoController;
import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TransacaoControllerTeste {

    private MockMvc mockMvc;

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private TransacaoController transacaoController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
    }

    @Test
    void testAdicionarTransacoes() throws Exception {
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now());

        mockMvc.perform(post("/api/transacao/adicionartransacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"valor\":100.0,\"timestamp\":\"2024-03-18T12:00:00Z\"}"))
                .andExpect(status().isOk());

        verify(transacaoService, times(1)).adicionarTransacao(any(Transacao.class));
    }

    @Test
    void testExibirTransacoes() throws Exception {
        when(transacaoService.listarTransacoes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/transacao/exibirtransacao"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(transacaoService, times(1)).listarTransacoes();
    }

    @Test
    void testDeletarTransacao() throws Exception {
        Transacao transacao = new Transacao(50.0, OffsetDateTime.now());

        mockMvc.perform(delete("/api/transacao/deletar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"valor\":50.0,\"timestamp\":\"2024-03-18T12:00:00Z\"}"))
                .andExpect(status().isOk());

        verify(transacaoService, times(1)).removerTransacao(any(Transacao.class));
    }

    @Test
    void testDeletarTodasTransacoes() throws Exception {
        mockMvc.perform(delete("/api/transacao/deletar/tudo"))
                .andExpect(status().isOk());

        verify(transacaoService, times(1)).removerTodasTransacoes();
    }
}