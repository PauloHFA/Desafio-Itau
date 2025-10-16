package com.example.demo.itau.service;

import com.example.demo.itau.model.Transacao;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    private final List<Transacao> listaTransacoes = new ArrayList<>();

    public List<Transacao> transacao60Segundos() {
        List<Transacao> ultimasTransacoes = new ArrayList<>();
        System.out.println("Todas as transações: " + listaTransacoes);
        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.UTC);
        for (Transacao transacao : listaTransacoes) {
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
            transacao.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
        }
        listaTransacoes.add(transacao);
        System.out.println("Adicionando transação: " + transacao);
    }

    // Retorna todas as transações
    public List<Transacao> listarTransacoes() {
        return new ArrayList<>(listaTransacoes);
    }

    // Remove uma transação específica por ID
    public boolean removerTransacaoPorId(String id) {
        Optional<Transacao> transacaoParaRemover = listaTransacoes.stream()
                .filter(transacao -> id.equals(transacao.getId()))
                .findFirst();

        if (transacaoParaRemover.isPresent()) {
            listaTransacoes.remove(transacaoParaRemover.get());
            return true;
        }
        return false;
    }

    // Remove todas as transações
    public void removerTodasTransacoes() {
        listaTransacoes.clear();
    }

    // Retorna a quantidade de transações
    public int quantidadeTransacoes() {
        return listaTransacoes.size();
    }

    // Remove uma transação específica
    public void removerTransacao(Transacao transacao) {
        listaTransacoes.remove(transacao);
    }
}