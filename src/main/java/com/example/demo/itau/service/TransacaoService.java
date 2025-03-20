package com.example.demo.itau.service;

import com.example.demo.itau.model.Transacao;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {

    private final List<Transacao> listatransacaos = new ArrayList<>();

    public List<Transacao> transacao60Segundos() {
        List<Transacao> ultimasTransacoes = new ArrayList<>();
        System.out.println("Todas as transações: " + listatransacaos);
        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.UTC); // Garantindo UTC
        for (Transacao transacao : listatransacaos) {
            System.out.println("Verificando transação: " + transacao);
            if (transacao.getTimestamp() != null && transacao.getTimestamp().isAfter(agora.minusSeconds(60))) {
                ultimasTransacoes.add(transacao);
            }
        }
        System.out.println("Últimas transações: " + ultimasTransacoes);
        return ultimasTransacoes;
    }

    // Adiciona uma transação à lista
    public void adicionarTransacao(Transacao transacao) {
        if (transacao.getTimestamp() == null) {
            transacao.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC)); // Correção do timestamp
        }
        listatransacaos.add(transacao);
        System.out.println("Adicionando transação: " + transacao);
    }

    // Retorna todas as transações
    public List<Transacao> listarTransacoes() { // Nome ajustado para seguir a convenção Java
        return new ArrayList<>(listatransacaos);
    }

    // Remove uma transação específica
    public void removerTransacao(Transacao transacao) {
        listatransacaos.remove(transacao);
    }

    // Remove todas as transações
    public void removerTodasTransacoes() {
        listatransacaos.clear();
    }
}
