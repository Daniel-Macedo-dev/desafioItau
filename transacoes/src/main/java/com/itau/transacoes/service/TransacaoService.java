package com.itau.transacoes.service;

import com.itau.transacoes.infrastructure.entities.Transacao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {
    private final List<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao){
        transacoes.add(transacao);
    }
    public List<Transacao> listarTransacoes(){
        return transacoes;
    }
    public void limparTransacoes(){
        transacoes.clear();
    }
}
