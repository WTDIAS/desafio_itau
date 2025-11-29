package br.com.gigalike.desafioItau.jwt;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final String TEST_SECRET = "minha-chave-secreta-super-segura-com-256-bits-no-minimo-para-funcionar";
    private static final String USERNAME = "admin";

    @BeforeEach
    void setUp(){
        jwtUtil = new JwtUtil();
        //simula o @Value utlizando reflection
        ReflectionTestUtils.setField(jwtUtil,"secret", TEST_SECRET);
        //PostConstruct manual
        jwtUtil.init();
    }

    @Test
    @DisplayName("Deve gerar token jwt valido com username")
    void deveGerarTokenJwtValido() {
        String token = jwtUtil.gerarToken(USERNAME);
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
        assertThat(token.split("\\.")).hasSize(3);
    }


    @Test
    @DisplayName("Deve extrair username corretamente do token")
    void deveExtrairUserNameCorretamenteDoToken() {
        //ARRANGE
        String token = jwtUtil.gerarToken(USERNAME);
        //ACT
        String userNameExtraido = jwtUtil.extrairUserName(token);
        //ASSERT
        assertThat(userNameExtraido).isEqualTo(USERNAME);
    }

    @Test
    @DisplayName("Deve gerar tokens diferentes")
    void deveGerarTokenDiferentes(){
        //ARRANGE+ACT
        String token1 = jwtUtil.gerarToken("admin");
        String token2 = jwtUtil.gerarToken("user");
        //ASSERT
        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar extrar de um token inválido")
    void deveLancarExcecaoAoTentarExtrarDeTokenInvalido(){
        //ARRANGE
        String tokenInvalido = "token inválido";
        //ACT+ASSERT
        Assertions.assertThrows(MalformedJwtException.class,()->jwtUtil.extrairUserName(tokenInvalido));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar extrair username com assinatura invalida")
    void deveLancarExcecaoAoTentarExtrairUserNameComAssinaturaInvalida(){
        //ARRANGE
        String token = jwtUtil.gerarToken(USERNAME);
        String tokenModificado = token.substring(0,token.length()-5) + "12345";
        //ACT+ASSERT
        Assertions.assertThrows(SignatureException.class,()->jwtUtil.extrairUserName(tokenModificado));
    }
}