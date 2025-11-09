package br.com.gigalike.desafioItau.controller;
import br.com.gigalike.desafioItau.dto.EstatisticaDto;
import br.com.gigalike.desafioItau.exception.BadRequestException;
import br.com.gigalike.desafioItau.exception.UnprocessableEntityException;
import br.com.gigalike.desafioItau.service.TransacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TransacaoController.class)
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransacaoService transacaoService;

    @Test
    @DisplayName("Deve retornar código HTTP 201 sem corpo ao salvar transação com dados válidos")
    void deveRetornar201SemCorpoAoTentarSalvarTransacaoComDadosValidos() throws Exception {
        //ARRANGE
        String json = """
                    {
                        "valor": 9.99,
                        "dataHora": "2025-01-01T22:08:00.000-03:00"
                    }
                """;
        //ACT & ASSERT
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        verify(transacaoService).adicionarTransacao(any());
    }

    @Test
    @DisplayName("Deve retornar código HTTP 422 ao tentar salvar transação com valor inválido")
    void deveRetornar422SemCorpoAoTentarSalvarTransacaoComValorInvalido() throws Exception {
        //ARRANGE
        String json = """
                    {
                        "valor": -9.99,
                        "dataHora": "2025-01-01T22:08:00.000-03:00"
                    }
                """;
        //ACT
        doThrow(new UnprocessableEntityException("")).when(transacaoService).adicionarTransacao(any());
        // ASSERT
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnprocessableEntity());

        verify(transacaoService).adicionarTransacao(any());
    }

    @Test
    @DisplayName("Deve retornar código HTTP 400 ao tentar salvar transação com json inválido")
    void deveRetornar400SemCorpoAoTentarSalvarTransacaoComJsonInvalido() throws Exception {
        //ARRANGE
        String json = """
                    {
                        "valor": "9.99",
                        "dataHora": "2025-01-01T22:08:00.000-03:00"
                    }
                """;
        //ACT
        doThrow(new BadRequestException("")).when(transacaoService).adicionarTransacao(any());
        // ASSERT
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(transacaoService).adicionarTransacao(any());
    }


    @Test
    @DisplayName("Deve retornar codigo http 200 sem corpo após limpar as transações salvas")
    void deveRetornarCodigo200SemCorpoAposLimparTransacoesSalvas() throws Exception {
        //ARRANGE & ACT & ASSERT
        mockMvc.perform(delete("/transacao")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(transacaoService).limparTransacoes();
    }


    @Test
    @DisplayName("Deve retornar um código http 200 com objeto EstatisticaDto no corpo para intervalo de tempo default.")
    void deveRetornarCodigo200ComEstatisticaDtoNoCorpoIntervaloDefault() throws Exception {
        //ARRANGE
        int intervaloDefault = 60;
        EstatisticaDto estatisticaDto = new EstatisticaDto(2,50.0, 12.5, 5.0, 20.0);
        //ACT
        when(transacaoService.calcularEstatistica(intervaloDefault)).thenReturn(estatisticaDto);
        // ASSERT
        mockMvc.perform(get("/estatistica")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contar").value(2))
                .andExpect(jsonPath("$.soma").value(50.0))
                .andExpect(jsonPath("$.media").value(12.5))
                .andExpect(jsonPath("$.min").value(5.0))
                .andExpect(jsonPath("$.maximo").value(20.0));
        verify(transacaoService).calcularEstatistica(intervaloDefault);
    }

    @Test
    @DisplayName("Deve retornar um código http 200 com objeto EstatisticaDto no corpo para intervalo de tempo personalizado")
    void deveRetornarCodigo200ComEstatisticaDtoNoCorpoIntervaloPersonalizado() throws Exception {
        //ARRANGE
        int intervaloPersonalizado = 75;
        EstatisticaDto estatisticaDto = new EstatisticaDto(2,50.0, 12.5, 5.0, 20.0);
        //ACT
        when(transacaoService.calcularEstatistica(intervaloPersonalizado)).thenReturn(estatisticaDto);
        // ASSERT
        mockMvc.perform(get("/estatistica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("segundos",String.valueOf(intervaloPersonalizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contar").value(2))
                .andExpect(jsonPath("$.soma").value(50.0))
                .andExpect(jsonPath("$.media").value(12.5))
                .andExpect(jsonPath("$.min").value(5.0))
                .andExpect(jsonPath("$.maximo").value(20.0));
        verify(transacaoService).calcularEstatistica(intervaloPersonalizado);
    }
}