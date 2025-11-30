package br.com.gigalike.desafioItau.exception;

/**
 * Esta classe é para representar a exceção que será lançada
 * quando for necessário enviar uma resposta HTTP Unprocessable
 *
 * @author Waldir Tiago Dias
 * @version 1.0, 11/2025
 */


public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(String message) {
        super(message);
    }
}
