package resource;

import DTO.requests.CriarUsuarioDTORequest;
import DTO.responses.CriarUsuarioDTOResponse;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import repository.UserRepository;
import service.UserService;

import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioTest {
    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @TestHTTPResource("/users")
    URI apiUrl;

    @Test
    @DisplayName("Deve conseguir criar um novo usuário")
    @Order(1)
    public void createUserTest(){
        CriarUsuarioDTORequest dados = CriarUsuarioDTORequest.builder()
                .username("Bambietta")
                .avatar("Basterbine.jpg")
                .build();

        Response resposta =
                given()
                    .contentType(ContentType.JSON)
                    .body(dados)
                .when()
                    .post(apiUrl)
                .then()
                    .extract().response();

        CriarUsuarioDTOResponse dadosResposta = resposta.as(CriarUsuarioDTOResponse.class);

        assertEquals(201,resposta.getStatusCode());
        assertNotNull(dadosResposta.getId());
        assertEquals(dados.getUsername(),dadosResposta.getUsername());
        assertEquals(dados.getAvatar(),dadosResposta.getAvatar());
        assertEquals(0,dadosResposta.getFollowersList().size());
        assertEquals(0,dadosResposta.getFollowingList().size());
    }

    @Test
    @DisplayName("Deve gerar bad Request ao tentar criar um usuário com dados incompletos")
    @Order(2)
    public void failCreateUserTest(){
        CriarUsuarioDTORequest dados = CriarUsuarioDTORequest.builder()
                .username("").avatar("goku.jpg").build();

        Response respostaRequisicao = given()
                .contentType(ContentType.JSON)
                .body(dados)
                .when()
                .post(apiUrl)
                .then()
                .extract().response();

        assertEquals(400,respostaRequisicao.getStatusCode());
    }

    @Test
    @DisplayName("Deve dar erro de conflito ao tentar cadastrar usuario que ja existe no sistema")
    @Order(3)
    public void failCreateDuplicateUserTest(){
        CriarUsuarioDTORequest dadosInput = CriarUsuarioDTORequest.builder()
                .username("gohan").avatar("gohan.jpg").build();

        userService.adicionarUsuario(dadosInput);

        var response = given()
                .contentType(ContentType.JSON)
                .body(dadosInput)
                .when()
                .post(apiUrl)
                .then()
                .extract().response();

        assertEquals(409,response.getStatusCode());


    }



}