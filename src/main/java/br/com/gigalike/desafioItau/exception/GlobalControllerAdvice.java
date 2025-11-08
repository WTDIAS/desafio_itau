package br.com.gigalike.desafioItau.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
