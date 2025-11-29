package br.com.gigalike.desafioItau.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.PrintWriter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private PrintWriter writer;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve processar requisição com token válido e autenticar usuário")
    void deveProcessarRequisicaoComTokenValidoEAutenticarUsuario() throws Exception {
        // ARRANGE
        String token = "Bearer valid.jwt.token";
        String username = "admin";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.extrairUserName("valid.jwt.token")).thenReturn(username);

        // ACT
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // ASSERT
        verify(jwtUtil).extrairUserName("valid.jwt.token");
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo(username);
    }

    @Test
    @DisplayName("Deve retornar 401 quando token for inválido")
    void deveRetornar401QuandoTokenForInvalido() throws Exception {
        // ARRANGE
        String token = "Bearer invalid.token";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.extrairUserName("invalid.token")).thenThrow(new JwtException("Token inválido"));
        when(response.getWriter()).thenReturn(writer);

        // ACT
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // ASSERT
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(writer).write("Token inválido ou expirado.");
        verify(filterChain,never()).doFilter(request, response);
    }

    @Test
    @DisplayName("Deve continuar a cadeia de filtros quando não houver header Authorization")
    void deveContinuarCadeiaFiltrosQuandoNaoHouverHeaderAuthorization() throws Exception {
        // ARRANGE
        when(request.getHeader("Authorization")).thenReturn(null);

        // ACT
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // ASSERT
        verify(jwtUtil, never()).extrairUserName(anyString());
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("Deve continuar a cadeia de filtros quando header não começar com Bearer")
    void deveContinuarCadeiaFiltrosQuandoHeaderNaoComecarComBearer() throws Exception {
        // ARRANGE
        when(request.getHeader("Authorization")).thenReturn("Basic dXNlcjpwYXNz");

        // ACT
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // ASSERT
        verify(jwtUtil, never()).extrairUserName(anyString());
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("Deve extrair token corretamente removendo prefixo Bearer")
    void deveExtrairTokenCorretamenteRemovendoPrefixoBearer() throws Exception {
        // ARRANGE
        String tokenCompleto = "Bearer meu.token.jwt";
        String tokenEsperado = "meu.token.jwt";

        when(request.getHeader("Authorization")).thenReturn(tokenCompleto);
        when(jwtUtil.extrairUserName(tokenEsperado)).thenReturn("admin");

        // ACT
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // ASSERT
        verify(jwtUtil).extrairUserName(tokenEsperado);
    }
}