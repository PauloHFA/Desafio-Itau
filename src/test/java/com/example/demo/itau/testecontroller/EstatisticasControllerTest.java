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

@ExtendWith(MockitoExtension.class) // Habilita Mockito no JUnit 5
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
                new Transacao(100.0, OffsetDateTime.now())

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

        List<Map<String, Double>> response = estatisticasController.getEstatisticas();

        assertFalse(response.isEmpty());
        assertEquals(300.0, response.get(0).get("maior"));
        assertEquals(100.0, response.get(0).get("menor"));
        assertEquals(3.0, response.get(0).get("contador"));
        assertEquals(600.0, response.get(0).get("soma"));
        assertEquals(200.0, response.get(0).get("media"));
    }

    @Test
    void testGetEstatisticasListaVazia() {
        when(transacaoService.transacao60Segundos()).thenReturn(Collections.emptyList());
        List<Map<String, Double>> response = estatisticasController.getEstatisticas();
        assertTrue(response.isEmpty());
    }

    @Test
    void testGetUltimasTransacoes() {
        when(transacaoService.transacao60Segundos()).thenReturn(transacoes);
        List<Transacao> response = estatisticasController.getUltimasTransacoes();
        assertEquals(3, response.size());
    }

    @Test
    void testContador() {
        when(transacaoService.Listartransacoes()).thenReturn(transacoes);
        when(estatisticasService.contador(transacoes)).thenReturn(3);
        assertEquals(3, estatisticasController.contador());
    }

    @Test
    void testCalcularSoma() {
        when(transacaoService.Listartransacoes()).thenReturn(transacoes);
        when(estatisticasService.calcularsoma(transacoes)).thenReturn(600.0);
        assertEquals(600.0, estatisticasController.calcularsoma());
    }

    @Test
    void testCalcularMedia() {
        when(transacaoService.Listartransacoes()).thenReturn(transacoes);
        when(estatisticasService.calcularmnedia(transacoes)).thenReturn(200.0);
        assertEquals(200.0, estatisticasController.media());
    }

    @Test
    void testMinimo() {
        when(transacaoService.Listartransacoes()).thenReturn(transacoes);
        when(estatisticasService.minimo(transacoes)).thenReturn(100.0);
        assertEquals(100.0, estatisticasController.menorvalor());
    }

    @Test
    void testMaximo() {
        when(transacaoService.Listartransacoes()).thenReturn(transacoes);
        when(estatisticasService.maximo(transacoes)).thenReturn(300.0);
        assertEquals(300.0, estatisticasController.maiorvalor());
    }
}
