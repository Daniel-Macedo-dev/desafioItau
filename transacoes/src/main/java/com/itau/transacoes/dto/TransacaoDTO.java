package com.itau.transacoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO {
    private Double valor;
    private OffsetDateTime dataHora;
}
