package com.example.demo.itau.model;

import ch.qos.logback.core.boolex.EvaluationException;
import lombok.*;

import java.time.Instant;
import java.time.OffsetDateTime;

@Data
@Getter
@Setter
public class Transacao {
    public double valor;
    public OffsetDateTime dataHora;
    private Instant timestamp = Instant.now();

    public Transacao(double valor, OffsetDateTime dataHora, Instant timestamp){
        this.valor = valor;
        this.dataHora = dataHora;
        this.timestamp = Instant.now();
    }
}