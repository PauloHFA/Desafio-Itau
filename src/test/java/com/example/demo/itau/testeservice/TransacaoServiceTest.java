package com.example.demo.itau.testeservice;

import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Habilita Mockito no JUnit 5
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService; // Classe real a ser testada

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os Mocks
        transacaoService.removertodastransacoes(); // Garante que começamos com uma lista vazia
    }

    @Test
    void testAdicionarTransacao() {
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now());

        transacaoService.adicionartransacao(transacao);
        List<Transacao> transacoes = transacaoService.Listartransacoes();

        assertEquals(1, transacoes.size());
        assertEquals(100.0, transacoes.get(0).getValor());
    }

    @Test
    void testListarTransacoes() {
        transacaoService.adicionartransacao(new Transacao(50.0, OffsetDateTime.now()));
        transacaoService.adicionartransacao(new Transacao(200.0, OffsetDateTime.now()));

        List<Transacao> transacoes = transacaoService.Listartransacoes();

        assertEquals(2, transacoes.size());
    }

    @Test
    void testRemoverTransacao() {
        Transacao transacao = new Transacao(300.0, OffsetDateTime.now());
        transacaoService.adicionartransacao(transacao);

        transacaoService.removertransacao(transacao);

        List<Transacao> transacoes = transacaoService.Listartransacoes();
        assertEquals(0, transacoes.size());
    }

    @Test
    void testTransacao60Segundos() {
        Transacao antiga = new Transacao(150.0, OffsetDateTime.now().minusSeconds(120));
        Transacao recente = new Transacao(250.0, OffsetDateTime.now().minusSeconds(30));

        transacaoService.adicionartransacao(antiga);
        transacaoService.adicionartransacao(recente);

        List<Transacao> ultimasTransacoes = transacaoService.transacao60Segundos();

        assertEquals(1, ultimasTransacoes.size());
        assertEquals(250.0, ultimasTransacoes.get(0).getValor());
    }
}