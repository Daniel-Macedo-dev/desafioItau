package com.itau.transacoes.controller;

import com.itau.transacoes.dto.EstatisticaDTO;
import com.itau.transacoes.dto.TransacaoDTO;
import com.itau.transacoes.service.EstatisticaService;
import com.itau.transacoes.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController {
    private final EstatisticaService estatisticaService;
    private final TransacaoService transacaoService;


}
