package com.example.demo.itau.testecontroller;

import com.example.demo.itau.controller.EstatisticasController;
import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.EstatisticasService;
import com.example.demo.itau.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstatisticasControllerTest {

    @InjectMocks
    private EstatisticasController estatisticasController;

    @Mock
    private EstatisticasService estatisticasService;

    @Mock
    private TransacaoService transacaoService;

    private List<Transacao> transacoes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transacoes = Arrays.asList(
                new Transacao(100.0, OffsetDateTime.now()),
                new Transacao(200.0, OffsetDateTime.now()),
                new Transacao(300.0, OffsetDateTime.now())
        );
    }

    @Test
    void testGetEstatisticas() {
        when(transacaoService.transacao60Segundos()).thenReturn(transacoes);
        when(estatisticasService.maximo(transacoes)).thenReturn(300.0);
        when(estatisticasService.minimo(transacoes)).thenReturn(100.0);
        when(estatisticasService.contador(transacoes)).thenReturn(3);
        when(estatisticasService.calcularsoma(transacoes)).thenReturn(600.0);
        when(estatisticasService.calcularmnedia(transacoes)).thenReturn(200.0);

        var response = estatisticasController.getEstatisticas();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        Map<String, Double> body = (Map<String, Double>) response.getBody();
        assertNotNull(body);
        assertEquals(300.0, body.get("maior"));
        assertEquals(100.0, body.get("menor"));
        assertEquals(3.0, body.get("contador"));
        assertEquals(600.0, body.get("soma"));
        assertEquals(200.0, body.get("media"));
    }

    @Test
    void testGetEstatisticasListaVazia() {
        when(transacaoService.transacao60Segundos()).thenReturn(Collections.emptyList());

        var response = estatisticasController.getEstatisticas();

        assertEquals(204, response.getStatusCodeValue()); // No Content
        assertNull(response.getBody());
    }

    @Test
    void testGetUltimasTransacoes() {
        when(transacaoService.transacao60Segundos()).thenReturn(transacoes);

        var response = estatisticasController.getUltimasTransacoes();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(3, response.getBody().size());
    }

    @Test
    void testContador() {
        when(transacaoService.listarTransacoes()).thenReturn(transacoes);
        when(estatisticasService.contador(transacoes)).thenReturn(3);

        var response = estatisticasController.contador();
        assertEquals(3, response.getBody());
    }

    @Test
    void testCalcularSoma() {
        when(transacaoService.listarTransacoes()).thenReturn(transacoes);
        when(estatisticasService.calcularsoma(transacoes)).thenReturn(600.0);

        var response = estatisticasController.calcularsoma();
        assertEquals(600.0, response.getBody());
    }

    @Test
    void testCalcularMedia() {
        when(transacaoService.listarTransacoes()).thenReturn(transacoes);
        when(estatisticasService.calcularmnedia(transacoes)).thenReturn(200.0);

        var response = estatisticasController.media();
        assertEquals(200.0, response.getBody());
    }

    @Test
    void testMinimo() {
        when(transacaoService.listarTransacoes()).thenReturn(transacoes);
        when(estatisticasService.minimo(transacoes)).thenReturn(100.0);

        var response = estatisticasController.menorvalor();
        assertEquals(100.0, response.getBody());
    }

    @Test
    void testMaximo() {
        when(transacaoService.listarTransacoes()).thenReturn(transacoes);
        when(estatisticasService.maximo(transacoes)).thenReturn(300.0);

        var response = estatisticasController.maiorvalor();
        assertEquals(300.0, response.getBody());
    }
}