package br.com.gigalike.desafioItau.dto;

/**
 * Este DTO é para tranferência de dados token + mensagem
 *
 * Este é utilizado para ser enviado para usuário contendo um token ou null e uma mensagem
 *
 * @author Waldir Tiago Dias
 * @version 1.0, 11/2025
 */

public record RespostaTokenDto(String message, String token) {
}
