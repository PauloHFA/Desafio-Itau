package com.example.demo.itau.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {
    private double valor;
    private OffsetDateTime timestamp;

    public Transacao(double valor){
        this.valor = valor;
        this.timestamp = OffsetDateTime.now(ZoneOffset.UTC);
    }
}