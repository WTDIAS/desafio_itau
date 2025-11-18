package br.com.gigalike.desafioItau.controller;
import br.com.gigalike.desafioItau.dto.RespostaTokenDto;
import br.com.gigalike.desafioItau.dto.UsuarioDto;
import br.com.gigalike.desafioItau.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
@Tag(name = "Login", description = "Endpoint para logar e utilizar a API. Como não é possível cadastrar usuário, deverá ser informado no corpo o seguinte JSON: " +
        "{\n" +
        "  \"username\": \"admin\",\n" +
        "  \"password\": \"1234\"\n" +
        "}")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = "Autentica o usuário e gera um token JWT",
            description = "Valida as credenciais do usuário. Em caso de sucesso, retorna o token de acesso para ser usado nos endpoints protegidos.")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retorno será um JSON com mensagem de login realizado com sucesso e o token.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RespostaTokenDto.class)
                    )
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "Retorno será um JSON com mensagem de Login ou senha inválido e o token null.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RespostaTokenDto.class)
                    )
            )
    })

    @PostMapping
    public ResponseEntity<RespostaTokenDto> login(@RequestBody UsuarioDto usuarioDto){
         return authService.autenticar(usuarioDto);
    }
}
