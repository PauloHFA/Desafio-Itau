package com.example.demo.itau.controller;

import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.TransacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoController.class);
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    // Endpoint para adicionar transação
    @PostMapping
    public ResponseEntity<?> adicionarTransacao(@RequestBody Transacao transacao) {
        try {
            logger.info("Recebendo requisição para adicionar transação: {}", transacao);

            // Validação manual - campos obrigatórios
            if (transacao.getValor() == null || transacao.getTimestamp() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Valor e timestamp são obrigatórios");
            }

            // Validação para valor não negativo
            if (transacao.getValor() < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Valor não pode ser negativo");
            }

            // Validação para timestamp não futuro
            if (transacao.getTimestamp().isAfter(java.time.OffsetDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Timestamp não pode ser uma data futura");
            }

            transacaoService.adicionarTransacao(transacao);

            logger.info("Transação adicionada com sucesso: {}", transacao.getId());
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (IllegalArgumentException e) {
            logger.warn("Requisição inválida: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erro interno ao adicionar transação: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao processar transação");
        }
    }

    // Endpoint para exibir todas as transações
    @GetMapping
    public ResponseEntity<List<Transacao>> listarTransacoes() {
        try {
            logger.info("Recebendo requisição para listar transações");

            List<Transacao> transacoes = transacaoService.listarTransacoes();

            if (transacoes.isEmpty()) {
                logger.info("Nenhuma transação encontrada");
                return ResponseEntity.noContent().build();
            }

            logger.info("Retornando {} transações", transacoes.size());
            return ResponseEntity.ok(transacoes);

        } catch (Exception e) {
            logger.error("Erro ao listar transações: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    // Endpoint para obter transações dos últimos 60 segundos
    @GetMapping("/ultimos-60-segundos")
    public ResponseEntity<List<Transacao>> obterTransacoesRecentes() {
        try {
            logger.info("Recebendo requisição para listar transações dos últimos 60 segundos");

            List<Transacao> transacoesRecentes = transacaoService.transacao60Segundos();

            if (transacoesRecentes.isEmpty()) {
                logger.info("Nenhuma transação recente encontrada");
                return ResponseEntity.noContent().build();
            }

            logger.info("Retornando {} transações recentes", transacoesRecentes.size());
            return ResponseEntity.ok(transacoesRecentes);

        } catch (Exception e) {
            logger.error("Erro ao listar transações recentes: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    // Endpoint para deletar uma transação específica por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarTransacao(@PathVariable String id) {
        try {
            logger.info("Recebendo requisição para deletar transação: {}", id);

            // Validação do ID
            if (id == null || id.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("ID é obrigatório");
            }

            boolean removida = transacaoService.removerTransacaoPorId(id);

            if (removida) {
                logger.info("Transação {} deletada com sucesso", id);
                return ResponseEntity.ok().build();
            } else {
                logger.warn("Transação {} não encontrada para deleção", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Transação não encontrada");
            }

        } catch (Exception e) {
            logger.error("Erro ao deletar transação {}: ", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar transação");
        }
    }

    // Endpoint para deletar todas as transações
    @DeleteMapping
    public ResponseEntity<?> deletarTodasTransacoes() {
        try {
            logger.info("Recebendo requisição para deletar todas as transações");

            int quantidade = transacaoService.quantidadeTransacoes();

            // Validação se há transações para deletar
            if (quantidade == 0) {
                logger.info("Nenhuma transação para deletar");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Nenhuma transação encontrada para deleção");
            }

            transacaoService.removerTodasTransacoes();

            logger.info("Todas as {} transações foram deletadas", quantidade);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            logger.error("Erro ao deletar todas as transações: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar todas as transações");
        }
    }
}