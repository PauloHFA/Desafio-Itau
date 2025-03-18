package com.example.demo.itau.model;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@Getter
@Setter
public class Transacao {
    public double valor;
    public OffsetDateTime dataHora;
    private OffsetDateTime timestamp = OffsetDateTime.now();

    public Transacao(double valor, OffsetDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.timestamp = timestamp;
    }
}