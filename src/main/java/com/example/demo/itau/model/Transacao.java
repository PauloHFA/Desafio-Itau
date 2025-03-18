package com.example.demo.itau.model;

import lombok.*;

import java.time.Instant;
import java.time.OffsetDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
public class Transacao {
    public double valor;
    public OffsetDateTime dataHora;
    Instant instant = Instant.now();
    private OffsetDateTime timestamp = OffsetDateTime.from(instant);

}