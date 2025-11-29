# Desafio Itaú - API de Estatísticas de Transações

![CI](https://github.com/WTDIAS/desafio_itau/actions/workflows/ci.yml/badge.svg)


Este projeto implementa uma API RESTful em Java com Spring Boot 3 para gerenciar transações e calcular estatísticas em tempo real, focando em transações que ocorreram nos últimos **60 segundos** (ou um intervalo personalizado).

## 🚀 Tecnologias e Ferramentas

O projeto foi desenvolvido com as seguintes tecnologias:

* **Java 17**
* **Spring Boot 3.3.4**
* **Maven**
* **Spring JWT**: Para garantir a segurança da API. 
* **Spring Boot Starter Web**: Para construir a API RESTful.
* **Spring Boot Starter Test**: Para testes unitários e de integração.
* **Spring Boot Starter Actuator**: Para monitoramento (endpoints `health`, `info`).
* **Springdoc OpenAPI UI**: Para documentação Swagger/OpenAPI.
* **Programação Funcional (Streams)**: Utilizada para cálculo eficiente de estatísticas em memória.
* **Records (Java 17)**: Utilizados para os DTOs (`TransacaoDto`, `EstatisticaDto`).

## 📂 Estrutura do Projeto

A organização segue o padrão do Spring Boot e está estruturada por camadas:

![Diretórios](src/main/resources/images/diretorios.png)

## 🛠️ Requisitos de Execução

Para rodar este projeto localmente, você precisará ter instalado:

* **JDK 17** ou superior.
* **Maven** 3.x.

## ⚙️ Como Executar

### 1. Clonar o Repositório

```bash
git clone https://github.com/WTDIAS/desafio_itau.git
```

### 2. Construir e Rodar a Aplicação. Use o Maven para construir e executar a aplicação:

```bash
# Compila e empacota a aplicação
mvn clean package

# Executa o JAR gerado (ajuste o nome do arquivo, se necessário)
java -jar target/desafioItau-0.0.1-SNAPSHOT.jar
```
A aplicação será iniciada na porta padrão do Spring Boot (geralmente 8080).

## 🧪 Como utilizar
* **1** - Com o Postman instalado.
* **2** - Para fazer as requisições é necessário fazer login através de uma requisição POST contendo o JSON. Será retornado um JSON contendo o token que será necessário para demais requisições.<br>
  {<br>
  "username":"admin",<br>
  "password":"1234"<br>
  }
![Login](src/main/resources/images/login-postman1.png)
* **3** - Para inserir uma transação, você deverá inserir o token adquirido durante o login na aba auth conforme a imagem abaixo.
![transacao-auth](src/main/resources/images/transacao-auth.png)
* **4** - Em seguida na aba Body, informe o JSON com valor e datahora conforme a imagem abaixo.<br>
{<br>
  "valor": 9.77,<br>
  "dataHora": "2025-11-29T10:10:29.078Z"<br>
}

![transacao-auth](src/main/resources/images/transacao-body.png)
* **5** - Para coletar a estatística, também será necessário inserir o token na aba auth. Você pode opcionalmente modificar o intervalo para cálculo da estatística, pois o intervalo default é de 60s.
![Estatistica-params](src/main/resources/images/estatistica-params2.png)
* **6** - Enjoy 😉
## 🗺️ Endpoints da API
A documentação completa da API (Swagger UI) estará disponível em http://localhost:8080/swagger-ui.html após a inicialização do servidor.

![Endpoints](src/main/resources/images/login-swegger.png)
![Endpoints](src/main/resources/images/endpoints.png)
![Endpoint - Post](src/main/resources/images/endpointPost.png)
![Endpoint - Delete](src/main/resources/images/endpointDelete.png)
![Endpoint - Get](src/main/resources/images/endpointGet.png)
## 📝 Nota  
A API utiliza a classe FormatadorNumerico para garantir que os valores de soma e media sejam formatados com exatamente duas casas decimais, usando o arredondamento HALF_UP.


## 💉 Teste de saúde, métricas, e muito mais da API
### Para verificação dos KPIs acesse o link abaixo pelo Postman
http://localhost:8080/actuator

![Endpoint - Get](src/main/resources/images/actuator.png)
## 🧪 Testes  
Os testes unitários garantem a cobertura das regras de negócio e o correto funcionamento dos controllers.  

### Testes de Transação

- Adição de transação válida, valor zero, nula, negativa, e data nula.
- Lançamento de exceções para valores nulos, negativos, ou data nula (UnprocessableEntityException).
- Cálculo de estatísticas para lista vazia, intervalo sem transações, intervalo com transações válidas.
- Teste do controller: Retorno de status HTTP 201 (POST /transacao) com dados válidos.
- Retorno de status HTTP 422 para falhas de validação.
- Retorno de status HTTP 400 para JSON inválido.
- Retorno de status HTTP 200 e corpo EstatisticaDto para requisição GET /estatistica (padrão e personalizada).

### Testes de Autenticação

- Retorno de código HTTP 200 e token ao autenticar com sucesso.
- Retorno de código HTTP 403 e token nulo ao tentar autenticar com username ou senha inválidos.
- Testes de controller: retorno de status HTTP apropriado e corpo da resposta para login inválido ou válido.
- Teste de filtro JWT: processa requisições com token válido e autentica usuário.
- Retorno de status 401 para token inválido ou expirado.
- Validação de ausência do header Authorization.
- Continuação da cadeia de filtros para header inválido.

### Testes de Utilidade

- Formatação de número: arredondamento para cima e para baixo para duas casas decimais.
- Manutenção do valor original se já possuir duas casas decimais ou sem casas decimais.
- Retorno zero para valores nulos.

### Testes de JWT

- Geração de token JWT válido e com username.
- Extração do username corretamente do token.
- Geração de tokens diferentes para usernames diferentes.
- Lançamento de exceção ao tentar extrair username de token inválido.
- Lançamento de exceção para assinatura inválida.

## 🔄 Para executar todos os testes:
```bash 

  mvn test
  
```
