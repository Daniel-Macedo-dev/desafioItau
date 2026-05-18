package com.itau.transacoes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransacaoApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @BeforeEach
    void setUp() throws Exception {
        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());
    }

    // ─── POST /transacao ──────────────────────────────────────────────────────

    @Test
    void postTransacao_valorPositivo_returns201SemCorpo() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson(100.0, OffsetDateTime.now().minusSeconds(5))))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_valorZero_returns201SemCorpo() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson(0.0, OffsetDateTime.now().minusSeconds(5))))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_valorNegativo_returns422SemCorpo() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson(-1.0, OffsetDateTime.now().minusSeconds(5))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_dataHoraFutura_returns422SemCorpo() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson(100.0, OffsetDateTime.now().plusSeconds(60))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_valorAusente_returns422SemCorpo() throws Exception {
        String json = "{\"dataHora\":\"" + OffsetDateTime.now().minusSeconds(5).format(ISO) + "\"}";
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_valorNulo_returns422SemCorpo() throws Exception {
        String json = "{\"valor\":null,\"dataHora\":\"" + OffsetDateTime.now().minusSeconds(5).format(ISO) + "\"}";
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_dataHoraAusente_returns422SemCorpo() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"valor\":100.0}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_dataHoraNula_returns422SemCorpo() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"valor\":100.0,\"dataHora\":null}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_jsonMalformado_returns400SemCorpo() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{broken"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_formatoDataInvalido_returns400SemCorpo() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"valor\":100.0,\"dataHora\":\"01/01/2025\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_tipoValorInvalido_returns400SemCorpo() throws Exception {
        String json = "{\"valor\":\"nao-e-numero\",\"dataHora\":\"" + OffsetDateTime.now().minusSeconds(5).format(ISO) + "\"}";
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    // ─── DELETE /transacao ────────────────────────────────────────────────────

    @Test
    void deleteTransacao_returns200SemCorpo() throws Exception {
        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void deleteTransacao_aposTransacoesAdicionadas_estatisticaRetornaZeros() throws Exception {
        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transacaoJson(100.0, OffsetDateTime.now().minusSeconds(5))));

        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.sum").value(0.0))
                .andExpect(jsonPath("$.avg").value(0.0))
                .andExpect(jsonPath("$.min").value(0.0))
                .andExpect(jsonPath("$.max").value(0.0));
    }

    // ─── GET /estatistica ─────────────────────────────────────────────────────

    @Test
    void getEstatistica_semTransacoes_retornaZeros() throws Exception {
        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.sum").value(0.0))
                .andExpect(jsonPath("$.avg").value(0.0))
                .andExpect(jsonPath("$.min").value(0.0))
                .andExpect(jsonPath("$.max").value(0.0));
    }

    @Test
    void getEstatistica_umaTransacaoRecente_retornaValoresCorretos() throws Exception {
        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transacaoJson(150.0, OffsetDateTime.now().minusSeconds(5))));

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.sum").value(150.0))
                .andExpect(jsonPath("$.avg").value(150.0))
                .andExpect(jsonPath("$.min").value(150.0))
                .andExpect(jsonPath("$.max").value(150.0));
    }

    @Test
    void getEstatistica_multiplasTransacoesRecentes_retornaAgregacoesCorretas() throws Exception {
        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transacaoJson(100.0, OffsetDateTime.now().minusSeconds(5))));
        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transacaoJson(200.0, OffsetDateTime.now().minusSeconds(5))));
        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transacaoJson(300.0, OffsetDateTime.now().minusSeconds(5))));

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.sum").value(600.0))
                .andExpect(jsonPath("$.avg").value(200.0))
                .andExpect(jsonPath("$.min").value(100.0))
                .andExpect(jsonPath("$.max").value(300.0));
    }

    @Test
    void getEstatistica_transacaoForaJanela60s_excluida() throws Exception {
        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transacaoJson(500.0, OffsetDateTime.now().minusMinutes(2))));

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.sum").value(0.0))
                .andExpect(jsonPath("$.avg").value(0.0))
                .andExpect(jsonPath("$.min").value(0.0))
                .andExpect(jsonPath("$.max").value(0.0));
    }

    @Test
    void getEstatistica_mistoAntigaERecente_incluiApenasUltimos60s() throws Exception {
        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transacaoJson(500.0, OffsetDateTime.now().minusMinutes(2))));
        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transacaoJson(100.0, OffsetDateTime.now().minusSeconds(5))));

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.sum").value(100.0))
                .andExpect(jsonPath("$.avg").value(100.0))
                .andExpect(jsonPath("$.min").value(100.0))
                .andExpect(jsonPath("$.max").value(100.0));
    }

    @Test
    void getEstatistica_camposInglesPresentes_camposPortuguesAusentes() throws Exception {
        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").exists())
                .andExpect(jsonPath("$.sum").exists())
                .andExpect(jsonPath("$.avg").exists())
                .andExpect(jsonPath("$.min").exists())
                .andExpect(jsonPath("$.max").exists())
                .andExpect(jsonPath("$.quantidade").doesNotExist())
                .andExpect(jsonPath("$.soma").doesNotExist())
                .andExpect(jsonPath("$.media").doesNotExist())
                .andExpect(jsonPath("$.minimo").doesNotExist())
                .andExpect(jsonPath("$.maximo").doesNotExist());
    }

    // ─── helper ───────────────────────────────────────────────────────────────

    private String transacaoJson(double valor, OffsetDateTime dataHora) {
        return "{\"valor\":" + valor + ",\"dataHora\":\"" + dataHora.format(ISO) + "\"}";
    }
}
