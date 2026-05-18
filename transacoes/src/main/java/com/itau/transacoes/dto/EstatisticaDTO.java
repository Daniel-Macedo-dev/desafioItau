package com.itau.transacoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticaDTO {
    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;
}
