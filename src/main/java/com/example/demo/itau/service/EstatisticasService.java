package com.example.demo.itau.service;

import com.example.demo.itau.model.Transacao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstatisticasService {

    public double maximo(List<Transacao> transacoes) {
        return transacoes.stream().mapToDouble(Transacao::getValor).max().orElse(0.0);
    }

    public double minimo(List<Transacao> transacoes) {
        return transacoes.stream().mapToDouble(Transacao::getValor).min().orElse(0.0);
    }

    public int contador(List<Transacao> transacoes) {
        return transacoes.size();
    }

    public double calcularsoma(List<Transacao> transacoes) {
        return transacoes.stream().mapToDouble(Transacao::getValor).sum();
    }

    public double calcularmnedia(List<Transacao> transacoes) {
        return transacoes.isEmpty() ? 0.0 : calcularsoma(transacoes) / contador(transacoes);
    }
}
//    public double filtrarporvalor(double valor) {
//        double transacaosFiltradas = 0;
//        for (int i = 0; i < transacaos.size(); i++) {
//            Transacao transacao = transacaos.get(i);
//            if(transacao.getValor() == valor){
//                transacaosFiltradas = transacao.getValor();
//            }
//        }
//        return transacaosFiltradas;
//    }