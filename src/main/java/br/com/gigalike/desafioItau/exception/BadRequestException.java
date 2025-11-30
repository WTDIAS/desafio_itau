package br.com.gigalike.desafioItau.exception;

/**
 * Esta classe é para representar a exceção que será lançada
 * quando for necessário enviar uma resposta HTTP Bad Request
 *
 * @author Waldir Tiago Dias
 * @version 1.0, 11/2025
 */

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
