package com.example.demo.itau.testeservice;

import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.EstatisticasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita Mockito no JUnit 5
public class EstatisticasServiceTest {

    @InjectMocks
    private EstatisticasService estatisticasService;

    @Mock
    private List<Transacao> transacoesMock;

    private List<Transacao> transacoesReais;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os Mocks

        transacoesReais = Arrays.asList(
                new Transacao(100.0, OffsetDateTime.now()),
                new Transacao(200.0, OffsetDateTime.now()),
                new Transacao(300.0, OffsetDateTime.now())
        );
    }

    @Test
    void testContador() {
        when(transacoesMock.size()).thenReturn(3); // Simula o tamanho da lista

        int count = estatisticasService.contador(transacoesMock);

        assertEquals(3, count);
        verify(transacoesMock, times(1)).size(); // Garante que o método foi chamado
    }

    @Test
    void testCalcularSoma() {
        when(transacoesMock.size()).thenReturn(3);
        when(transacoesMock.get(0)).thenReturn(transacoesReais.get(0));
        when(transacoesMock.get(1)).thenReturn(transacoesReais.get(1));
        when(transacoesMock.get(2)).thenReturn(transacoesReais.get(2));

        double soma = estatisticasService.calcularsoma(transacoesMock);

        assertEquals(600.0, soma);
        verify(transacoesMock, times(3)).get(anyInt());
    }

    @Test
    void testCalcularMedia() {
        when(transacoesMock.size()).thenReturn(3);
        when(transacoesMock.get(0)).thenReturn(transacoesReais.get(0));
        when(transacoesMock.get(1)).thenReturn(transacoesReais.get(1));
        when(transacoesMock.get(2)).thenReturn(transacoesReais.get(2));

        double media = estatisticasService.calcularmnedia(transacoesMock);

        assertEquals(200.0, media);
        verify(transacoesMock, times(3)).get(anyInt());
    }

    @Test
    void testMinimo() {
        when(transacoesMock.size()).thenReturn(3);
        when(transacoesMock.get(0)).thenReturn(transacoesReais.get(0));
        when(transacoesMock.get(1)).thenReturn(transacoesReais.get(1));
        when(transacoesMock.get(2)).thenReturn(transacoesReais.get(2));

        double min = estatisticasService.minimo(transacoesMock);

        assertEquals(100.0, min);
        verify(transacoesMock, times(3)).get(anyInt());
    }

    @Test
    void testMaximo() {
        when(transacoesMock.size()).thenReturn(3);
        when(transacoesMock.get(0)).thenReturn(transacoesReais.get(0));
        when(transacoesMock.get(1)).thenReturn(transacoesReais.get(1));
        when(transacoesMock.get(2)).thenReturn(transacoesReais.get(2));

        double max = estatisticasService.maximo(transacoesMock);

        assertEquals(300.0, max);
        verify(transacoesMock, times(3)).get(anyInt());
    }
}