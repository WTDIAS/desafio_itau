package br.com.gigalike.desafioItau.service;

import br.com.gigalike.desafioItau.dto.TransacaoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {
    private TransacaoService transacaoService;
    @BeforeEach
    void setUp(){
        transacaoService = new TransacaoService();
    }

    @Test
    @DisplayName("Deve adicionar a transação válida e não retornar exceção ")
    void deveAdicionarTransacaoValidaENaoRetornarExcecao() {
        //ARRANGE
        TransacaoDto transacaoDtoValida = new TransacaoDto(9.99D, OffsetDateTime.parse("2025-01-01T22:08:00.000-03:00"));
        //ACT & ASSERT
        Assertions.assertDoesNotThrow(()->transacaoService.adicionarTransacao(transacaoDtoValida));
    }

/*
    TransacaoDto transacaoDtoValidaZero = new TransacaoDto(0D, OffsetDateTime.parse("2025-01-01T22:08:00.000-03:00"));
    TransacaoDto transacaoDtoInvalidaValor = new TransacaoDto(-9.99D, OffsetDateTime.parse("2025-01-01T22:08:00.000-03:00"));
    TransacaoDto transacaoDtoInvalidaDataFutura = new TransacaoDto(9.99D, OffsetDateTime.parse("2025-01-01T22:10:00.000-03:00"));
*/

    @Test
    void limparTransacoes() {
    }

    @Test
    void calcularEstatistica() {
    }
}