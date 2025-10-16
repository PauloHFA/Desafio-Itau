package com.example.demo.itau.controller;

import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.TransacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.DoubleSummaryStatistics;

@RestController
@RequestMapping("/api/valores")
public class EstatisticasController {

    private static final Logger logger = LoggerFactory.getLogger(EstatisticasController.class);

    private final TransacaoService transacaoService;

    @Autowired
    public EstatisticasController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    /**
     * Retorna estatísticas das transações nos últimos 60 segundos
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Double>> getEstatisticas() {
        try {
            List<Transacao> ultimasTransacoes = transacaoService.transacao60Segundos();

            Map<String, Double> estatisticas = new HashMap<>();

            if (ultimasTransacoes == null || ultimasTransacoes.isEmpty()) {
                estatisticas.put("count", 0.0);
                estatisticas.put("sum", 0.0);
                estatisticas.put("avg", 0.0);
                estatisticas.put("min", 0.0);
                estatisticas.put("max", 0.0);
                return ResponseEntity.ok(estatisticas);
            }

            DoubleSummaryStatistics stats = ultimasTransacoes.stream()
                    .mapToDouble(Transacao::getValor)
                    .summaryStatistics();

            estatisticas.put("count", (double) stats.getCount());
            estatisticas.put("sum", stats.getSum());
            estatisticas.put("avg", stats.getAverage());
            estatisticas.put("min", stats.getMin());
            estatisticas.put("max", stats.getMax());

            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            logger.error("Erro ao calcular estatísticas: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", 0.0));
        }
    }

    /**
     * Retorna as transações ocorridas nos últimos 60 segundos
     */
    @GetMapping("/ultimas/transacoes")
    public ResponseEntity<List<Transacao>> getUltimasTransacoes() {
        try {
            List<Transacao> transacoes = transacaoService.transacao60Segundos();
            return ResponseEntity.ok(transacoes);
        } catch (Exception e) {
            logger.error("Erro ao obter últimas transações: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    /**
     * Retorna o valor máximo das transações dos últimos 60 segundos
     */
    @GetMapping("/maior")
    public ResponseEntity<Double> getMaiorValor() {
        try {
            List<Transacao> transacoes = transacaoService.transacao60Segundos();
            double max = transacoes.stream()
                    .mapToDouble(Transacao::getValor)
                    .max()
                    .orElse(0.0);
            return ResponseEntity.ok(max);
        } catch (Exception e) {
            logger.error("Erro ao calcular maior valor: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0.0);
        }
    }

    /**
     * Retorna o valor mínimo das transações dos últimos 60 segundos
     */
    @GetMapping("/menor")
    public ResponseEntity<Double> getMenorValor() {
        try {
            List<Transacao> transacoes = transacaoService.transacao60Segundos();
            double min = transacoes.stream()
                    .mapToDouble(Transacao::getValor)
                    .min()
                    .orElse(0.0);
            return ResponseEntity.ok(min);
        } catch (Exception e) {
            logger.error("Erro ao calcular menor valor: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0.0);
        }
    }

    /**
     * Retorna a soma das transações dos últimos 60 segundos
     */
    @GetMapping("/soma")
    public ResponseEntity<Double> getSoma() {
        try {
            List<Transacao> transacoes = transacaoService.transacao60Segundos();
            double soma = transacoes.stream()
                    .mapToDouble(Transacao::getValor)
                    .sum();
            return ResponseEntity.ok(soma);
        } catch (Exception e) {
            logger.error("Erro ao calcular soma: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0.0);
        }
    }

    /**
     * Retorna a média das transações dos últimos 60 segundos
     */
    @GetMapping("/media")
    public ResponseEntity<Double> getMedia() {
        try {
            List<Transacao> transacoes = transacaoService.transacao60Segundos();
            double media = transacoes.stream()
                    .mapToDouble(Transacao::getValor)
                    .average()
                    .orElse(0.0);
            return ResponseEntity.ok(media);
        } catch (Exception e) {
            logger.error("Erro ao calcular média: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0.0);
        }
    }

    /**
     * Retorna a quantidade de transações dos últimos 60 segundos
     */
    @GetMapping("/contador")
    public ResponseEntity<Long> getContador() {
        try {
            List<Transacao> transacoes = transacaoService.transacao60Segundos();
            long count = transacoes.size();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            logger.error("Erro ao calcular contador: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0L);
        }
    }
}