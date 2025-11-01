package com.itau.transacoes.infrastructure.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    private double amount;
    private OffsetDateTime dateTime;
}
