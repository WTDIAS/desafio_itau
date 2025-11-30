package br.com.gigalike.desafioItau.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Esta classe possui metodos para auxiliar nas funções da JWT
 *
 * @author Waldir Tiago Dias
 * @version 1.0, 11/2025
 */

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey SECRET_KEY;

    @PostConstruct
    public void init(){
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    private static final long EXPERATION_TIME = 3600000;

    /**
     * Este metodo gera um novo token com base no username fornecido
     * e utilizando uma chave secreta para isso.
     *
     * @author Waldir Tiago Dias
     * @version 1.0, 11/2025
     */
    public String gerarToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPERATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }


    /**
     * Este metodo faz a validação e extração do username de um token.
     *
     * @author Waldir Tiago Dias
     * @version 1.0, 11/2025
     */
    public String extrairUserName(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
