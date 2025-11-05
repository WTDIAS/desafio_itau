package br.com.gigalike.desafioItau.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DesafioItauGlobalControllerAdvice {
    @ExceptionHandler(DesafioItauException422.class)
    public ResponseEntity<Void> desafioItauException422(){
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(DesafioItauException400.class)
    public ResponseEntity<Void> desafioItauException400(){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> exceptionBadJson(){
        return ResponseEntity.badRequest().build();
    }

}
