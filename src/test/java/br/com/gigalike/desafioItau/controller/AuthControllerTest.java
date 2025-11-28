package br.com.gigalike.desafioItau.controller;
import br.com.gigalike.desafioItau.dto.RespostaTokenDto;
import br.com.gigalike.desafioItau.dto.UsuarioDto;
import br.com.gigalike.desafioItau.jwt.JwtUtil;
import br.com.gigalike.desafioItau.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean  // ← ADICIONAR ESTE
    private JwtUtil jwtUtil;

    @MockBean  // ← ADICIONAR ESTE TAMBÉM
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("Deve retornar código http 200 para login válido.")
    void deveRetornarHttp200ParaLoginValido() throws Exception {
        //ARRANGE
        String json = """
                        {               
                        "username": "admin",
                        "password": "1234"
                    }
                """;
        String token = "123456789.exemplo.token";
        RespostaTokenDto respostaTokenDto = new RespostaTokenDto("Login realizado com sucesso",token);
        when(authService.autenticar(any(UsuarioDto.class))).thenReturn(ResponseEntity.ok(respostaTokenDto));

        //ACT + ASSERT
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login realizado com sucesso"))
                .andExpect(jsonPath("$.token").value(token));
        verify(authService).autenticar(any(UsuarioDto.class));
    }

    @Test
    @DisplayName("Deve retornar código HTTP 403 ao tentar fazer login com credenciais inválidas")
    void deveRetornar403AoTentarFazerLoginComCredenciaisInvalidas() throws Exception {
        // ARRANGE
        String json = """
                {
                    "username": "usuario_invalido",
                    "password": "senha_errada"
                }
                """;

        RespostaTokenDto respostaEsperada = new RespostaTokenDto(
                "Login ou senha inválido",
                null
        );

        when(authService.autenticar(any(UsuarioDto.class)))
                .thenReturn(ResponseEntity.status(403).body(respostaEsperada));

        // ACT & ASSERT
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Login ou senha inválido"))
                .andExpect(jsonPath("$.token").isEmpty());

        verify(authService).autenticar(any(UsuarioDto.class));
    }

    @Test
    @DisplayName("Deve retornar código HTTP 403 ao tentar fazer login com username vazio")
    void deveRetornar403AoTentarFazerLoginComUsernameVazio() throws Exception {
        // ARRANGE
        String json = """
                {
                    "username": "",
                    "password": "1234"
                }
                """;

        RespostaTokenDto respostaEsperada = new RespostaTokenDto(
                "Login ou senha inválido",
                null
        );

        when(authService.autenticar(any(UsuarioDto.class)))
                .thenReturn(ResponseEntity.status(403).body(respostaEsperada));

        // ACT & ASSERT
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Login ou senha inválido"))
                .andExpect(jsonPath("$.token").isEmpty());

        verify(authService).autenticar(any(UsuarioDto.class));
    }

    @Test
    @DisplayName("Deve retornar código HTTP 403 ao tentar fazer login com password vazio")
    void deveRetornar403AoTentarFazerLoginComPasswordVazio() throws Exception {
        // ARRANGE
        String json = """
                {
                    "username": "admin",
                    "password": ""
                }
                """;

        RespostaTokenDto respostaEsperada = new RespostaTokenDto(
                "Login ou senha inválido",
                null
        );

        when(authService.autenticar(any(UsuarioDto.class)))
                .thenReturn(ResponseEntity.status(403).body(respostaEsperada));

        // ACT & ASSERT
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Login ou senha inválido"))
                .andExpect(jsonPath("$.token").isEmpty());

        verify(authService).autenticar(any(UsuarioDto.class));
    }

    @Test
    @DisplayName("Deve retornar código HTTP 400 ao enviar JSON mal formatado")
    void deveRetornar400AoEnviarJsonMalFormatado() throws Exception {
        // ARRANGE
        String jsonInvalido = """
                {
                    "username": "admin"
                    "password": "1234"
                }
                """;

        // ACT & ASSERT
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }
}