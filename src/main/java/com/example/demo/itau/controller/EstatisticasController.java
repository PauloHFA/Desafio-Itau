package com.example.demo.itau.controller;

import com.example.demo.itau.service.EstatisticasService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/valores")
public class EstatisticasController {
    private final EstatisticasService estatisticasService;

    public EstatisticasController(EstatisticasService estatisticasService) {
        this.estatisticasService = estatisticasService;
    }

    @GetMapping("/estatisticas")
    public Map<String, Double> getEstatisticas() {
        Map<String, Double> estatisticas = new HashMap<>();

        estatisticas.put("maior", estatisticasService.maximo());
        estatisticas.put("menor", estatisticasService.minimo());
        estatisticas.put("contador", (double) estatisticasService.contador());
        estatisticas.put("soma", estatisticasService.calcularsoma());
        estatisticas.put("media", estatisticasService.calcularmnedia());

        return estatisticas;
    }

    @GetMapping("/contador")
    public double contador (@RequestBody EstatisticasService estatisticasService){
        return estatisticasService.contador();
    }
    @GetMapping("/soma")
    public double calcularsoma (@RequestBody EstatisticasService estatisticasService){
        return  estatisticasService.calcularsoma();
    }
    @GetMapping("/media")
    public double calcularmedia (@RequestBody EstatisticasService estatisticasService){
        return estatisticasService.calcularmnedia();
    }
    @GetMapping("/menor")
    public double menorvalor(@RequestBody EstatisticasService estatisticasService){
        return  estatisticasService.minimo();
    }
    @GetMapping("/maior")
    public double maiorvalor(@RequestBody EstatisticasService estatisticasService){
        return estatisticasService.maximo();
    }
}