package com.example.demo.itau.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.EstatisticasService;
import com.example.demo.itau.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/valores")
public class EstatisticasController {
    private EstatisticasService estatisticasService;
    private TransacaoService transacaoService;

    @Autowired
    public EstatisticasController(EstatisticasService estatisticasService, TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
        this.estatisticasService = estatisticasService;
    }

    @GetMapping("/estatisticas")
    public List<Map<String, Double>> getEstatisticas() {
        List<Transacao> ultimasTransacoes = transacaoService.transacao60Segundos();

        if (ultimasTransacoes.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Double> estatisticas = new HashMap<>();
        estatisticas.put("maior", estatisticasService.maximo(ultimasTransacoes));
        estatisticas.put("menor", estatisticasService.minimo(ultimasTransacoes));
        estatisticas.put("contador", (double) estatisticasService.contador(ultimasTransacoes));
        estatisticas.put("soma", estatisticasService.calcularsoma(ultimasTransacoes));
        estatisticas.put("media", estatisticasService.calcularmnedia(ultimasTransacoes));

        return Collections.singletonList(estatisticas);
    }
    //Teste da lista de ultimas transacoes
    @GetMapping("/ultimas/transacoes")
    public List<Transacao> getUltimasTransacoes() {
        System.out.println("lista ultimas transacoes" +transacaoService.transacao60Segundos());
        return transacaoService.transacao60Segundos();
    }

    @GetMapping("/contador")
    public double contador (){
        List<Transacao> transacaos = transacaoService.Listartransacoes();
        return estatisticasService.contador(transacaos);
    }
    @GetMapping("/soma")
    public double calcularsoma (){
        List<Transacao> transacaos= transacaoService.Listartransacoes();
        return  estatisticasService.calcularsoma(transacaos);
    }
    @GetMapping("/media")
    public  double media(){
        List<Transacao> transacaos = transacaoService.Listartransacoes();
        return estatisticasService.calcularmnedia(transacaos);
    }
    @GetMapping("/menor")
    public double menorvalor(){
        List<Transacao> transacaos = transacaoService.Listartransacoes();
        return  estatisticasService.minimo(transacaos);
    }
    @GetMapping("/maior")
    public double maiorvalor(){
        List<Transacao> transacaos = transacaoService.Listartransacoes();
        return estatisticasService.maximo(transacaos);
    }
}