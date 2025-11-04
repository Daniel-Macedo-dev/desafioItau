package com.itau.transacoes.controller;

import com.itau.transacoes.dto.EstatisticaDTO;
import com.itau.transacoes.dto.TransacaoDTO;
import com.itau.transacoes.infrastructure.entities.Transacao;
import com.itau.transacoes.service.EstatisticaService;
import com.itau.transacoes.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController {
    private final EstatisticaService estatisticaService;
    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Void> adicionarTransacao(@RequestBody TransacaoDTO transacaoDTO){
        return transacaoService.adicionarTransacao(transacaoDTO);
    }

    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticaDTO> obterEstatisticas(){
        List<Transacao> transacoes = transacaoService.listarTransacoes();
        EstatisticaDTO estatisticas = estatisticaService.calcularEstatisticas(transacoes);
        return ResponseEntity.ok(estatisticas);
    }

    @DeleteMapping
    public ResponseEntity<Void> limparTransacoes(){
        transacaoService.limparTransacoes();
        return ResponseEntity.ok().build();
    }

}
