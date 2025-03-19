package com.example.demo.itau.testeservice;

import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoService mockTransacaoService;

    @BeforeEach
    void setUp() {
        transacaoService.removerTodasTransacoes();
    }

    @Test
    void testAdicionarTransacao() {
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now());

        doNothing().when(mockTransacaoService).adicionarTransacao(transacao);
        mockTransacaoService.adicionarTransacao(transacao);

        verify(mockTransacaoService, times(1)).adicionarTransacao(transacao);
    }

    @Test
    void testListarTransacoes() {
        Transacao transacao1 = new Transacao(50.0, OffsetDateTime.now());
        Transacao transacao2 = new Transacao(200.0, OffsetDateTime.now());

        when(mockTransacaoService.listarTransacoes()).thenReturn(Arrays.asList(transacao1, transacao2));

        List<Transacao> transacoes = mockTransacaoService.listarTransacoes();

        assertEquals(2, transacoes.size());
        verify(mockTransacaoService, times(1)).listarTransacoes();
    }

    @Test
    void testRemoverTransacao() {
        Transacao transacao = new Transacao(300.0, OffsetDateTime.now());

        doNothing().when(mockTransacaoService).removerTransacao(transacao);
        mockTransacaoService.removerTransacao(transacao);

        verify(mockTransacaoService, times(1)).removerTransacao(transacao);
    }

    @Test
    void testTransacao60Segundos() {
        Transacao antiga = new Transacao(150.0, OffsetDateTime.now());
        Transacao recente = new Transacao(250.0, OffsetDateTime.now());

        when(mockTransacaoService.transacao60Segundos()).thenReturn(Collections.singletonList(recente));

        List<Transacao> ultimasTransacoes = mockTransacaoService.transacao60Segundos();

        assertEquals(1, ultimasTransacoes.size());
        assertEquals(250.0, ultimasTransacoes.get(0).getValor());
        verify(mockTransacaoService, times(1)).transacao60Segundos();
    }
}
