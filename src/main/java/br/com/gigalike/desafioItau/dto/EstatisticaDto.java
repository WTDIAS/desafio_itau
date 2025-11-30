package br.com.gigalike.desafioItau.dto;

/**
 * DTO para transferência de dados do cálculo de estatística
 *
 * Este é utilizado para retornar a resposta para usuário ao acessar o endpoint /estatistica
 *
 * @author Waldir Tiago Dias
 * @version 1.0, 11/2025
 */

public record EstatisticaDto(long contar, double soma, double media, double min, double maximo) {
}
