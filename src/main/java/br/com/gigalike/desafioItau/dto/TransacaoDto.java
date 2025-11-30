package br.com.gigalike.desafioItau.dto;
import java.time.OffsetDateTime;

/**
 * Este DTO é utilizado para receber dados de uma transação
 *
 * Este receberá os dados do JSON para enviado no endpoint /transação
 * que será utilizado para cadastrar uma nova transação.
 *
 * @author Waldir Tiago Dias
 * @version 1.0, 11/2025
 */

public record TransacaoDto(Double valor, OffsetDateTime dataHora) {
}


