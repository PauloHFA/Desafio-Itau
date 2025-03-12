package com.example.demo.itau.service;

import com.example.demo.itau.model.Transacao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TransacaoService {

    private final List<Transacao> listatransacaos = new ArrayList<>();

    public void adicionartransacao(Transacao transacao){
        System.out.println("Lista de Transações: " + listatransacaos);
        listatransacaos.add(transacao);

    }

    public List<Transacao> Listartransacoes(){
        return listatransacaos;
    }

    public double filtrarporvalor(double valor) {
        double transacaosFiltradas = 0;
        for (int i = 0; i < listatransacaos.size(); i++) {
            Transacao transacao = listatransacaos.get(i);
            if(transacao.getValor() == valor){
                transacaosFiltradas = transacao.getValor();
            }
        }
        return transacaosFiltradas;
    }

    //remover transacao
    public void removertransacao(Transacao transacao){
        listatransacaos.remove(transacao);
    }

    //remover todas transacoes
    public void removertodastransacoes(Transacao transacao){
        listatransacaos.removeAll((Collection<?>) transacao);
    }
}