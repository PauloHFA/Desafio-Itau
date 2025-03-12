package com.example.demo.itau.controller;

import com.example.demo.itau.model.Transacao;
import com.example.demo.itau.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/adicionartransacao")
    public void adicionarTransacoes(@RequestBody Transacao transacao){
        trasacaoService.adicionartransacao(transacao);
    }

    @GetMapping("/exibirtransacao")
    public List<Transacao> exibirTransacoes(){
        System.out.println("Transações: " + trasacaoService.Listartransacoes());
        return trasacaoService.Listartransacoes();
    }
}