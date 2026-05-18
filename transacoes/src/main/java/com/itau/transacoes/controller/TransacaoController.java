package com.itau.transacoes.controller;

import com.itau.transacoes.dto.EstatisticaDTO;
import com.itau.transacoes.dto.TransacaoDTO;
import com.itau.transacoes.exceptions.TransacaoInvalidaException;
import com.itau.transacoes.infrastructure.entities.Transacao;
import com.itau.transacoes.service.EstatisticaService;
import com.itau.transacoes.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransacaoController {
    private final EstatisticaService estatisticaService;
    private final TransacaoService transacaoService;

    @PostMapping("/transacao")
    public ResponseEntity<Void> adicionarTransacao(@RequestBody TransacaoDTO transacaoDTO){
        if (transacaoDTO.getValor() == null) {
            throw new TransacaoInvalidaException("O valor da transação é obrigatório");
        }
        Transacao transacao = new Transacao();
        transacao.setValor(transacaoDTO.getValor());
        transacao.setDataHora(transacaoDTO.getDataHora());

        transacaoService.adicionarTransacao(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticaDTO> obterEstatisticas(){
        List<Transacao> transacoes = transacaoService.listarTransacoes();
        EstatisticaDTO estatisticas = estatisticaService.calcularEstatisticas(transacoes);
        return ResponseEntity.ok(estatisticas);
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> limparTransacoes(){
        transacaoService.limparTransacoes();
        return ResponseEntity.ok().build();
    }

}
