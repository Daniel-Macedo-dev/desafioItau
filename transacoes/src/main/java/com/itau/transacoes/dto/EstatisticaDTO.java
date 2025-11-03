package com.itau.transacoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticaDTO {
    private double soma;
    private double media;
    private double maximo;
    private double minimo;
    private long quantidade;
}
