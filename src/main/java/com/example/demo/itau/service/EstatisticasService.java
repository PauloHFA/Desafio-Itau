package com.example.demo.itau.service;

import com.example.demo.itau.model.Transacao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstatisticasService {
    private List<Transacao> transacaos;

    public EstatisticasService(List<Transacao> transacaos) {
        this.transacaos = transacaos;
    }

    //metodo que calcula quantidade de transacoes
    public int contador(List<Transacao> transacaos) {
        int count = 0;
        for (int i = 0; i < transacaos.size(); i++) {
            count++;
        }
        return count;
    }

    //metodo calcula soma das transacoes
    public double calcularsoma(List<Transacao> transacaos){
        double soma = 0;
        for (int i = 0; i < transacaos.size(); i++) {
            soma+= transacaos.get(i).getValor();
        }
        return soma;
    }

    //metodo que calcula a media das transacoes
    public double calcularmnedia(List<Transacao> transacaos){
        //o que eu vou precisar? vou precisar armazenar essa media
        double valor = 0;
        int numeroDeTransacoes = transacaos.size();

        for (int i = 0; i < numeroDeTransacoes; i++) {
            valor+= transacaos.get(i).getValor();

        }
        if (numeroDeTransacoes > 0) {
            return valor / numeroDeTransacoes;  // Calcula e retorna a média
        } else {
            return 0;  // Retorna 0 caso não haja transações
        }
    }

    //metodo para calcular o menor valor das transacoes
    public double minimo(List<Transacao> transacaos){
        double flagmin = 0;
        double valor = 0;
        for (int i = 0; i < transacaos.size(); i++) {
            valor+=transacaos.get(i).getValor();
            if (flagmin < valor){
                flagmin = valor;
            }
        }
        return flagmin;
    }

    //metodo para calcular o maior valor das transacoes
    public double maximo(List<Transacao> transacaos){
        double flagmax = 0;
        double valor = 0;
        for (int i = 0; i < transacaos.size(); i++) {
            valor += transacaos.get(i).getValor();
        }
        if (flagmax > valor){
            flagmax = valor;
        }
        return flagmax;
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
}