package com.example.demo.itau.controller;

import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.TransacaoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {
    private final TransacaoService trasacaoService;

    private final List<Transacao> transacaos = new ArrayList<>();

    public TransacaoController(TransacaoService trasacaoService) {
        this.trasacaoService = trasacaoService;
    }
    //endpoint para adicionar transacao em transacaos
    @PostMapping("/adicionartransacao")
    public void adicionarTransacoes(@RequestBody Transacao transacao){
        trasacaoService.adicionartransacao(transacao);
    }
    //endpoint para exibir as transacoes adicionadas em transacaos
    @GetMapping("/exibirtransacao")
    public List<Transacao> exibirTransacoes(){
        System.out.println("Transações: " + trasacaoService.Listartransacoes());
        return trasacaoService.Listartransacoes();
    }
    //Endpoint para deletar a transação
    @DeleteMapping("/deletar")
    public void  deletarTransacao(@RequestBody Transacao transacao){
        trasacaoService.removertransacao(transacao);
    }
}