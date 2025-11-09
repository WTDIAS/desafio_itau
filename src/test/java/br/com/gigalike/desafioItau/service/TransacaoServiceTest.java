package br.com.gigalike.desafioItau.service;
import br.com.gigalike.desafioItau.dto.EstatisticaDto;
import br.com.gigalike.desafioItau.dto.TransacaoDto;
import br.com.gigalike.desafioItau.exception.UnprocessableEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

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

    @Test
    @DisplayName("Deve adicionar a transação válida e não retornar exceção para valor igual a zero")
    void deveAdicionarTransacaoValidaENaoRetornarExcecaoValorZero() {
        //ARRANGE
        TransacaoDto transacaoDtoValida = new TransacaoDto(0D, OffsetDateTime.parse("2025-01-01T22:08:00.000-03:00"));
        //ACT & ASSERT
        Assertions.assertDoesNotThrow(()->transacaoService.adicionarTransacao(transacaoDtoValida));
    }

    @Test
    @DisplayName("Deve lançar a exceção umprocessableEntity se valor for nullo")
    void deveLancarExcecaoSeValorNullo(){
        //ARRANGE
        TransacaoDto transacaoInvalida = new TransacaoDto(null, OffsetDateTime.parse("2025-01-01T22:08:00.000-03:00"));
        //ACT & ASSERT
        Assertions.assertThrows(UnprocessableEntityException.class,()->transacaoService.adicionarTransacao(transacaoInvalida));
    }

    @Test
    @DisplayName("Deve lançar a exceção umprocessableEntity se dataHora for nullo")
    void deveLancarExcecaoSeDataHoraNullo(){
        //ARRANGE
        TransacaoDto transacaoInvalida = new TransacaoDto(9.99D, null);
        //ACT & ASSERT
        Assertions.assertThrows(UnprocessableEntityException.class,()->transacaoService.adicionarTransacao(transacaoInvalida));
    }

    @Test
    @DisplayName("Deve lançar a exceção umprocessableEntity se valor negativo")
    void deveLancarExcecaoSeValorNegativo(){
        //ARRANGE
        TransacaoDto transacaoInvalida = new TransacaoDto(-9.99D, OffsetDateTime.parse("2025-01-01T22:08:00.000-03:00"));
        //ACT & ASSERT
        Assertions.assertThrows(UnprocessableEntityException.class,()->transacaoService.adicionarTransacao(transacaoInvalida));
    }

    @Test
    @DisplayName("Deve lançar a exceção umprocessableEntity para data futura")
    void deveLancarExcecaoSeDataFutura(){
        //ARRANGE
        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.of("-03:00"));
        TransacaoDto transacaoInvalida = new TransacaoDto(-9.99D, agora.plusMinutes(1L));
        //ACT & ASSERT
        Assertions.assertThrows(UnprocessableEntityException.class,()->transacaoService.adicionarTransacao(transacaoInvalida));
    }

    @Test
    @DisplayName("Não deve retornar exceção ao limpar a lista de transações.")
    void naoDeveRetornarExcecaoAoLimparAsTransacoes() {
        //ARRANGE & ACT & ASSERT
        Assertions.assertDoesNotThrow(()->transacaoService.limparTransacoes());
    }

    @Test
    @DisplayName("Deve retornar um objeto EstatisticaDto devidadamente preenchido.")
    void deveRetornarEstatisticaDtoDevidamentePreenchido() {
        //ARRANGE
        int intervaloDefault = 60;
        EstatisticaDto estatisticaDtoEsperado = new EstatisticaDto(
                4,
                179.96,
                44.99,
                29.99,
                59.99
        );
        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.of("-03:00"));
        List<TransacaoDto> transacoes = List.of(
                new TransacaoDto(59.99, agora.minusSeconds(50)),
                new TransacaoDto(49.99, agora.minusSeconds(50)),
                new TransacaoDto(39.99, agora.minusSeconds(50)),
                new TransacaoDto(29.99, agora.minusSeconds(50))
        );
        transacoes.forEach(transacaoService::adicionarTransacao);

        //ACT
        EstatisticaDto estatisticaDtoRetorno = transacaoService.calcularEstatistica(intervaloDefault);

        //ASSERT
        Assertions.assertEquals(estatisticaDtoRetorno,estatisticaDtoEsperado);
    }

    @Test
    @DisplayName("Deve retornar um objeto EstatisticaDto devidadamente preenchido para apenas 3 das 4 transações pois uma esta fora do intervalo.")
    void deveRetornarEstatisticaDtoDevidamentePreenchidoUmaForaDoIntervalo() {
        //ARRANGE
        int intervaloDiferente = 70;
        EstatisticaDto estatisticaDtoEsperado = new EstatisticaDto(
                3,
                149.97,
                49.99,
                39.99,
                59.99

        );
        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.of("-03:00"));
        List<TransacaoDto> transacoes = List.of(
                new TransacaoDto(59.99, agora.minusSeconds(50)),
                new TransacaoDto(49.99, agora.minusSeconds(50)),
                new TransacaoDto(39.99, agora.minusSeconds(50)),
                new TransacaoDto(29.99, agora.minusSeconds(90))
        );
        transacoes.forEach(transacaoService::adicionarTransacao);

        //ACT
        EstatisticaDto estatisticaDtoRetorno = transacaoService.calcularEstatistica(intervaloDiferente);

        //ASSERT
        Assertions.assertEquals(estatisticaDtoRetorno,estatisticaDtoEsperado);
    }

    @Test
    @DisplayName("Deve retornar um objeto EstatisticaDto com todos os campos zero para lista de transações vazia.")
    void deveRetornarEstatisticaDtoComCamposZeradosQuandoParaListaVazia() {
        //ARRANGE
        int intervaloDefault = 60;
        EstatisticaDto estatisticaDtoEsperado = new EstatisticaDto(
                0,
                0.00,
                0.00,
                0.00,
                0.00
        );
        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.of("-03:00"));

        //ACT
        EstatisticaDto estatisticaDtoRetorno = transacaoService.calcularEstatistica(intervaloDefault);

        //ASSERT
        Assertions.assertEquals(estatisticaDtoRetorno,estatisticaDtoEsperado);
    }

    @Test
    @DisplayName("Deve retornar um objeto EstatisticaDto com todos os campos quando não houver transações no intervalo definido.")
    void deveRetornarEstatisticaDtoComCamposZeradosParaIntervaloSemTransacoes() {
        //ARRANGE
        int intervaloDefault = 60;
        EstatisticaDto estatisticaDtoEsperado = new EstatisticaDto(
                0,
                0.00,
                0.00,
                0.00,
                0.00
        );
        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.of("-03:00"));
        List<TransacaoDto> transacoes = List.of(
                new TransacaoDto(59.99, agora.minusSeconds(70)),
                new TransacaoDto(49.99, agora.minusSeconds(70)),
                new TransacaoDto(39.99, agora.minusSeconds(70)),
                new TransacaoDto(29.99, agora.minusSeconds(70))
        );
        transacoes.forEach(transacaoService::adicionarTransacao);

        //ACT
        EstatisticaDto estatisticaDtoRetorno = transacaoService.calcularEstatistica(intervaloDefault);

        //ASSERT
        Assertions.assertEquals(estatisticaDtoRetorno,estatisticaDtoEsperado);
    }

}