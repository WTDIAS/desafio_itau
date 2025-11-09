package br.com.gigalike.desafioItau.utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FormatadorNumericoTest {

    @Test
    @DisplayName("Deve retornar um double com duas casas decimais arredondando para cima para para terceiro valor igual ou maior que 5")
    void deveRetornarUmDoubleComDuasCasasDecimaisParaCima() {
        //ARRANGE
        double valorValido = 9.125;
        double valorEsperado = 9.13;
        //ACT
        double valorRetornado = FormatadorNumerico.formatarParaDuasCasasDecimais(valorValido);
        // ASSERT
        assertEquals(valorEsperado,valorRetornado);

    }

    @Test
    @DisplayName("Deve retornar um double com duas casas decimais mantendo as duas casas decimais para terceiro valor menor que 5")
    void deveRetornarUmDoubleComDuasCasasDecimaisParaBaixo() {
        //ARRANGE
        double valorValido = 9.114;
        double valorEsperado = 9.11;
        //ACT
        double valorRetornado = FormatadorNumerico.formatarParaDuasCasasDecimais(valorValido);
        // ASSERT
        assertEquals(valorEsperado,valorRetornado);

    }

    @Test
    @DisplayName("Deve retornar um double mantendo o valor quando tiver apenas duas casas decimais.")
    void deveRetornarUmDoubleMantendoOValor() {
        //ARRANGE
        double valorValido = 9.10;
        double valorEsperado = 9.10;
        //ACT
        double valorRetornado = FormatadorNumerico.formatarParaDuasCasasDecimais(valorValido);
        // ASSERT
        assertEquals(valorEsperado,valorRetornado);

    }

    @Test
    @DisplayName("Deve retornar um double com duas casas decimais zero quando o valor não possuir casas decimais.")
    void deveRetornarUmDoubleComDuasCasasDecimaisParaValorSemCasasDecimais() {
        //ARRANGE
        double valorValido = 9;
        double valorEsperado = 9.00;
        //ACT
        double valorRetornado = FormatadorNumerico.formatarParaDuasCasasDecimais(valorValido);
        // ASSERT
        assertEquals(valorEsperado,valorRetornado);

    }

    @Test
    @DisplayName("Deve retornar um double zero caso valor seja null")
    void deveRetornarUmDoubleZeroParaValorNull() {
        //ARRANGE
        Double valorInvalido = null;
        double valorEsperado = 0.00;
        //ACT
        double valorRetornado = FormatadorNumerico.formatarParaDuasCasasDecimais(valorInvalido);
        // ASSERT
        assertEquals(valorEsperado,valorRetornado);

    }
}