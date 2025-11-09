package com.itau.transacoes.service;

import com.itau.transacoes.exceptions.TransacaoInvalidaException;
import com.itau.transacoes.exceptions.ValorNegativoException;
import com.itau.transacoes.infrastructure.entities.Transacao;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {
    private final List<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao) {
        validarTransacao(transacao);
        transacoes.add(transacao);
    }

    public List<Transacao> listarTransacoes() {
        return transacoes;
    }

    public void limparTransacoes() {
        transacoes.clear();

    }

    private void validarTransacao(Transacao transacao){
        if (transacao.getValor() <= 0) {
            throw new ValorNegativoException("O valor da transação deve ser positivo.");
        }
        if(transacao.getDataHora() == null){
            throw new TransacaoInvalidaException("A data e hora da transação são podem ser nulas");
        }
        if(transacao.getDataHora().isAfter(OffsetDateTime.now())){
            throw new TransacaoInvalidaException("A data da transação não pode ser futura");
        }
    }

}
