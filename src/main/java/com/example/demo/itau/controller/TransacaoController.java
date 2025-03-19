package com.example.demo.itau.controller;

import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.TransacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoController.class);

    private final TransacaoService trasacaoService;
    private final List<Transacao> transacaos = new ArrayList<>();

    public TransacaoController(TransacaoService trasacaoService) {
        this.trasacaoService = trasacaoService;
    }

    // Endpoint para adicionar transação em transacaos
    @PostMapping("/adicionartransacao")
    public ResponseEntity<?> adicionarTransacoes(@RequestBody Transacao transacao) {
        try {
            trasacaoService.adicionarTransacao(transacao);
            return ResponseEntity.status(HttpStatus.CREATED).build(); // Retorna 201 Created após adicionar
        } catch (Exception e) {
            logger.error("Erro ao adicionar transação: ", e);  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao adicionar transação");
        }
    }

    // Endpoint para exibir as transações adicionadas em transacaos
    @GetMapping("/exibirtransacao")
    public ResponseEntity<List<Transacao>> exibirTransacoes() {
        try {
            List<Transacao> transacoes = trasacaoService.listarTransacoes();
            if (transacoes.isEmpty()) {
                return ResponseEntity.noContent().build();  // Retorna 204 No Content se não houver transações
            }
            return ResponseEntity.ok(transacoes);
        } catch (Exception e) {
            logger.error("Erro ao exibir transações: ", e);  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    // Endpoint para deletar a transação
    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarTransacao(@RequestBody Transacao transacao) {
        try {
            trasacaoService.removerTransacao(transacao);
            return ResponseEntity.ok().build();  // Retorna 200 OK após deletar
        } catch (Exception e) {
            logger.error("Erro ao deletar transação: ", e);  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar transação");
        }
    }

    // Endpoint para deletar todas as transações
    @DeleteMapping("/deletar/tudo")
    public ResponseEntity<?> deletartudo(@RequestBody Transacao transacao) {
        try {
            trasacaoService.removerTodasTransacoes();
            return ResponseEntity.ok().build();  // Retorna 200 OK após deletar todas
        } catch (Exception e) {
            logger.error("Erro ao deletar todas as transações: ", e);  // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar todas as transações");
        }
    }
}