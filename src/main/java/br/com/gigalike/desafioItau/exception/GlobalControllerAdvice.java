package br.com.gigalike.desafioItau.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controlador global para tratamento centralizado de exceções da aplicação e
 * retorna respostas HTTP apropriadas de forma padronizada.
 *
 * @author Waldir Tiago Dias
 * @version 1.0, 11/2025
 */


@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Void> unprocessableEntity(){
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> badRequest(){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> exceptionBadJson(){
        return ResponseEntity.badRequest().build();
    }

}
