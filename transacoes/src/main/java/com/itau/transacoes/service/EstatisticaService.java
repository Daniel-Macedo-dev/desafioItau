package com.itau.transacoes.service;

import com.itau.transacoes.dto.EstatisticaDTO;
import com.itau.transacoes.infrastructure.entities.Transacao;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class EstatisticaService {

    public EstatisticaDTO calcularEstatisticas(List<Transacao> transacoes) {
        OffsetDateTime agora = OffsetDateTime.now();

        List<Transacao> ultimos60s = transacoes.stream()
                .filter(t -> t.getDataHora().isAfter(agora.minusMinutes(1)))
                .toList();

        double soma = ultimos60s.stream().mapToDouble(Transacao::getValor).sum();
        double media = ultimos60s.isEmpty() ? 0.0 : soma / ultimos60s.size();
        double maximo = ultimos60s.stream().mapToDouble(Transacao::getValor).max().orElse(0.0);
        double minimo = ultimos60s.stream().mapToDouble(Transacao::getValor).min().orElse(0.0);

        return new EstatisticaDTO(soma, media, maximo, minimo, ultimos60s.size());
    }
}
