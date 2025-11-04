package com.itau.transacoes.controller;

import com.itau.transacoes.dto.EstatisticaDTO;
import com.itau.transacoes.dto.TransacaoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController {
    private final EstatisticaDTO estatisticaDTO;
    private final TransacaoDTO transacaoDTO;


}
