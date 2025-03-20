package com.example.demo.itau.controller;

import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.EstatisticasService;
import com.example.demo.itau.service.TransacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/valores")
public class EstatisticasController {

    private static final Logger logger = LoggerFactory.getLogger(EstatisticasController.class);

    private EstatisticasService estatisticasService;
    private TransacaoService transacaoService;

    @Autowired
    public EstatisticasController(EstatisticasService estatisticasService, TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
        this.estatisticasService = estatisticasService;
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<?> getEstatisticas() {
        try {
            List<Transacao> ultimasTransacoes = transacaoService.transacao60Segundos();

            if (ultimasTransacoes == null || ultimasTransacoes.isEmpty()) {
                return ResponseEntity.noContent().build(); // Retorna 204 No Content
            }

            Map<String, Double> estatisticas = new HashMap<>();
            estatisticas.put("maior", estatisticasService.maximo(ultimasTransacoes));
            estatisticas.put("menor", estatisticasService.minimo(ultimasTransacoes));
            estatisticas.put("contador", (double) estatisticasService.contador(ultimasTransacoes));
            estatisticas.put("soma", estatisticasService.calcularsoma(ultimasTransacoes));
            estatisticas.put("media", estatisticasService.calcularmnedia(ultimasTransacoes));

            System.out.println("Últimas transações: " + ultimasTransacoes);
            System.out.println("Estatísticas calculadas: " + estatisticas);

            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            // Registra a exceção com mais detalhes
            System.err.println("Erro ao calcular estatísticas: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro interno no servidor", "detalhe", e.getMessage()));
        }
    }
    //testar novamente o getultimastransacoes que esta me retornando apenas um 200ok mas nao exibe minha lista das ultimas transacoes
    @GetMapping("/ultimas/transacoes")
    public ResponseEntity<List<Transacao>> getUltimasTransacoes() {
        try {
            List<Transacao> transacoes = transacaoService.transacao60Segundos();
            return ResponseEntity.ok(transacoes);
        } catch (Exception e) {
            logger.error("Erro ao obter últimas transações: ", e);  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/contador")
    public ResponseEntity<?> contador() {
        try {
            List<Transacao> transacaos = transacaoService.listarTransacoes();
            return ResponseEntity.ok(estatisticasService.contador(transacaos));
        } catch (Exception e) {
            logger.error("Erro ao calcular contador: ", e);  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro interno ao calcular contador"));
        }
    }

    @GetMapping("/soma")
    public ResponseEntity<?> calcularsoma() {
        try {
            List<Transacao> transacaos = transacaoService.listarTransacoes();
            return ResponseEntity.ok(estatisticasService.calcularsoma(transacaos));
        } catch (Exception e) {
            logger.error("Erro ao calcular soma: ", e);  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro interno ao calcular soma"));
        }
    }

    @GetMapping("/media")
    public ResponseEntity<?> media() {
        try {
            List<Transacao> transacaos = transacaoService.listarTransacoes();
            return ResponseEntity.ok(estatisticasService.calcularmnedia(transacaos));
        } catch (Exception e) {
            logger.error("Erro ao calcular média: ", e);  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro interno ao calcular média"));
        }
    }

    @GetMapping("/menor")
    public ResponseEntity<?> menorvalor() {
        try {
            List<Transacao> transacaos = transacaoService.listarTransacoes();
            return ResponseEntity.ok(estatisticasService.minimo(transacaos));
        } catch (Exception e) {
            logger.error("Erro ao calcular menor valor: ", e);  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro interno ao calcular menor valor"));
        }
    }

    @GetMapping("/maior")
    public ResponseEntity<?> maiorvalor() {
        try {
            List<Transacao> transacaos = transacaoService.listarTransacoes();
            return ResponseEntity.ok(estatisticasService.maximo(transacaos));
        } catch (Exception e) {
            logger.error("Erro ao calcular maior valor: ", e);  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro interno ao calcular maior valor"));
        }
    }
}