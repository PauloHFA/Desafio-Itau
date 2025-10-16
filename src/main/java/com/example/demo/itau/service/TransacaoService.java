package com.example.demo.itau.service;

import com.example.demo.itau.model.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TransacaoService {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoService.class);
    private final List<Transacao> listaTransacoes = new ArrayList<>();

    // Métricas de performance
    private final AtomicLong totalTransacoesProcessadas = new AtomicLong(0);
    private final AtomicLong tempoTotalProcessamento = new AtomicLong(0);

    public List<Transacao> transacao60Segundos() {
        long inicio = System.currentTimeMillis();
        logger.debug("Iniciando cálculo de transações dos últimos 60 segundos");

        try {
            List<Transacao> ultimasTransacoes = new ArrayList<>();
            OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.UTC);

            for (Transacao transacao : listaTransacoes) {
                if (transacao.getTimestamp() != null &&
                        transacao.getTimestamp().isAfter(agora.minusSeconds(60))) {
                    ultimasTransacoes.add(transacao);
                }
            }

            return ultimasTransacoes;
        } finally {
            long fim = System.currentTimeMillis();
            long duracao = fim - inicio;
            tempoTotalProcessamento.addAndGet(duracao);
            logger.info("Cálculo de transações recentes concluído em {} ms", duracao);
        }
    }

    // Adiciona uma transação à lista com validações
    public void adicionarTransacao(Transacao transacao) {
        long inicio = System.currentTimeMillis();

        try {
            // Validação adicional na service
            if (transacao.getValor() < 0) {
                throw new IllegalArgumentException("Valor não pode ser negativo");
            }

            if (transacao.getTimestamp() == null) {
                transacao.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            }

            listaTransacoes.add(transacao);
            totalTransacoesProcessadas.incrementAndGet();

        } finally {
            long duracao = System.currentTimeMillis() - inicio;
            logger.debug("Transação adicionada em {} ms", duracao);
        }
    }

    // Retorna métricas de performance
    public Map<String, Object> getMetricas() {
        Map<String, Object> metricas = new HashMap<>();

        long total = totalTransacoesProcessadas.get();
        long tempoTotal = tempoTotalProcessamento.get();
        double tempoMedio = total > 0 ? (double) tempoTotal / total : 0;

        metricas.put("totalTransacoesProcessadas", total);
        metricas.put("transacoesArmazenadas", listaTransacoes.size());
        metricas.put("tempoTotalProcessamentoMs", tempoTotal);
        metricas.put("tempoMedioPorTransacaoMs", String.format("%.2f", tempoMedio));
        metricas.put("timestamp", OffsetDateTime.now(ZoneOffset.UTC).toString());

        return metricas;
    }

    // Métodos existentes mantidos...
    public List<Transacao> listarTransacoes() {
        return new ArrayList<>(listaTransacoes);
    }

    public boolean removerTransacaoPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        Optional<Transacao> transacaoParaRemover = listaTransacoes.stream()
                .filter(transacao -> id.equals(transacao.getId()))
                .findFirst();

        if (transacaoParaRemover.isPresent()) {
            listaTransacoes.remove(transacaoParaRemover.get());
            return true;
        }
        return false;
    }

    public void removerTodasTransacoes() {
        listaTransacoes.clear();
    }

    public int quantidadeTransacoes() {
        return listaTransacoes.size();
    }
}