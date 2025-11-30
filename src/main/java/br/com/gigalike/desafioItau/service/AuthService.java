package br.com.gigalike.desafioItau.service;
import br.com.gigalike.desafioItau.dto.RespostaTokenDto;
import br.com.gigalike.desafioItau.dto.UsuarioDto;
import br.com.gigalike.desafioItau.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Este metodo é responsável por verificar se usuário está logado,
 * e solicitar a geraração um token.
 *
 * @author Waldir Tiago Dias
 * @version 1.0, 11/2025
 */

@Service
public class AuthService {
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.userNameAdmin}")
    private String userNameAdmin;
    @Value("${jwt.userPasswordAdmin}")
    private String userPasswordAdmin;

    public ResponseEntity<RespostaTokenDto> autenticar(UsuarioDto usuarioDto){
        if(userNameAdmin.equals(usuarioDto.username()) && userPasswordAdmin.equals(usuarioDto.password())){
            String token = jwtUtil.gerarToken(usuarioDto.username());
            return ResponseEntity.ok().body(new  RespostaTokenDto("Login realizado com sucesso",token));
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RespostaTokenDto("Login ou senha inválido",null));
        }
    }
}
