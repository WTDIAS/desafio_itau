package br.com.gigalike.desafioItau.service;

import br.com.gigalike.desafioItau.dto.RespostaTokenDto;
import br.com.gigalike.desafioItau.dto.UsuarioDto;
import br.com.gigalike.desafioItau.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private JwtUtil jwtUtil;

    private final String ADMIN_USERNAME = "";
    private final String ADMIN_PASSWORD = "";
    private final String TOKEN = "mocked_jwt_token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "userNameAdmin", ADMIN_USERNAME);
        ReflectionTestUtils.setField(authService, "userPasswordAdmin", ADMIN_PASSWORD);
    }

    @Test
    @DisplayName("Deve retornar código http 200 e um token ao autenticar com sucesso ")
    void deveRetornar200ComTolkenAoAutenticarComSucesso() {
        //ARRANGE
        UsuarioDto usuarioDtoValido = new UsuarioDto(ADMIN_USERNAME,ADMIN_PASSWORD);
        when(jwtUtil.gerarToken(ADMIN_USERNAME)).thenReturn(TOKEN);
        //ACT
        ResponseEntity<RespostaTokenDto> resposta = authService.autenticar(usuarioDtoValido);
        //ASSERT
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        RespostaTokenDto respostaTokenDto = resposta.getBody();
        assertNotNull(respostaTokenDto);
        assertNotNull(respostaTokenDto.token());
        assertEquals("Login realizado com sucesso",respostaTokenDto.message());
        assertEquals(TOKEN,respostaTokenDto.token());
    }

    @Test
    @DisplayName("Deve retornar código http 403 e token null ")
    void deveRetornar403SemTolkenAoTentarAutenticarComUsernameErrado() {
        //ARRANGE
        UsuarioDto usuarioDtoValido = new UsuarioDto("userNameErrado",ADMIN_PASSWORD);
        //ACT
        ResponseEntity<RespostaTokenDto> resposta = authService.autenticar(usuarioDtoValido);
        //ASSERT
        assertEquals(HttpStatus.FORBIDDEN, resposta.getStatusCode());
        RespostaTokenDto respostaTokenDto = resposta.getBody();
        assertNotNull(respostaTokenDto);
        assertEquals("Login ou senha inválido",respostaTokenDto.message());
        assertNull(respostaTokenDto.token());
    }

    @Test
    @DisplayName("Deve retornar código http 403 e token null ")
    void deveRetornar403SemTolkenAoTentarAutenticarComPasswordErrado() {
        //ARRANGE
        UsuarioDto usuarioDtoValido = new UsuarioDto(ADMIN_USERNAME,"passwordErrado");
        //ACT
        ResponseEntity<RespostaTokenDto> resposta = authService.autenticar(usuarioDtoValido);
        //ASSERT
        assertEquals(HttpStatus.FORBIDDEN, resposta.getStatusCode());
        RespostaTokenDto respostaTokenDto = resposta.getBody();
        assertNotNull(respostaTokenDto);
        assertEquals("Login ou senha inválido",respostaTokenDto.message());
        assertNull(respostaTokenDto.token());
    }

}