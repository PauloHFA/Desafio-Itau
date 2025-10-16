package com.example.demo.itau.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Transacao {
    private String id;
    private Double valor;
    private OffsetDateTime timestamp;

    // Construtor com valor - gera ID automaticamente
    public Transacao(double valor) {
        this.id = UUID.randomUUID().toString();
        this.valor = valor;
        this.timestamp = OffsetDateTime.now(ZoneOffset.UTC);
    }

    // Construtor completo para casos específicos
    public Transacao(String id, double valor, OffsetDateTime timestamp) {
        this.id = id;
        this.valor = valor;
        this.timestamp = timestamp != null ? timestamp : OffsetDateTime.now(ZoneOffset.UTC);
    }

    // Construtor com valor e timestamp específico
    public Transacao(double valor, OffsetDateTime timestamp) {
        this.id = UUID.randomUUID().toString();
        this.valor = valor;
        this.timestamp = timestamp != null ? timestamp : OffsetDateTime.now(ZoneOffset.UTC);
    }
}