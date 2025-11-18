package br.com.gigalike.desafioItau.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

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

    public String gerarToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPERATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String extrairUserName(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
