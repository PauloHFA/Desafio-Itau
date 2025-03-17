package com.example.demo.itau.service;

import com.example.demo.itau.model.Transacao;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {

    private final List<Transacao> listatransacaos = new ArrayList<>();

    // Método que retorna as transações dos últimos 60 segundos
    public List<Transacao> transacao60Segundos() {
        Instant agora = Instant.now();
        List<Transacao> ultimasTransacoes = new ArrayList<>();

        System.out.println("Todas as transações: " + listatransacaos);

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
    public void adicionartransacao(Transacao transacao) {
        if(transacao.getTimestamp() == null){
            transacao.setTimestamp(Instant.now());
        }
        listatransacaos.add(transacao);
        System.out.println("Adicionando transação: " + transacao);
    }

    // Retorna todas as transações
    public List<Transacao> Listartransacoes() {
        return new ArrayList<>(listatransacaos);
    }

    // Remove uma transação específica
    public void removertransacao(Transacao transacao) {
        listatransacaos.remove(transacao);
    }

    // Remove todas as transações
    public void removertodastransacoes() {
        listatransacaos.clear();
    }
}