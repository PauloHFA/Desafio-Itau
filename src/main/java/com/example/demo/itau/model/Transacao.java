package com.example.demo.itau.model;

import ch.qos.logback.core.boolex.EvaluationException;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
public class Transacao {
    public double valor;
    public OffsetDateTime dataHora;

}