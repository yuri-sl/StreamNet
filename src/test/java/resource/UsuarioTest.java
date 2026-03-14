package resource;

import DTO.requests.CriarUsuarioDTORequest;
import DTO.responses.CriarTweetDTOResponse;
import DTO.responses.CriarUsuarioDTOResponse;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

    CriarUsuarioDTORequest dados_user1;
    CriarUsuarioDTORequest dados_user2;
    CriarUsuarioDTORequest dados_user3;

    CriarUsuarioDTORequest dados_incompletos_user2;

    CriarUsuarioDTOResponse resposta_user1;
    CriarUsuarioDTOResponse resposta_user2;
    CriarUsuarioDTOResponse resposta_user3;

    @BeforeEach()
    @Transactional
    public void setUp(){
        userRepository.deleteAll();

        dados_user1 = CriarUsuarioDTORequest.builder()
                .username("Bambietta")
                .avatar("Basterbine.jpg")
                .build();

        dados_user2 = CriarUsuarioDTORequest.builder()
                .username("Goku")
                .avatar("Gokuu.jpg")
                .build();

        dados_incompletos_user2 = CriarUsuarioDTORequest.builder()
                .username("").avatar("goku.jpg").build();

        dados_user3 = CriarUsuarioDTORequest.builder()
                .username("gohan").avatar("gohan.jpg").build();

    }

    @Test
    @DisplayName("Deve conseguir criar um novo usuário")
    @Order(1)
    public void createUserTest(){

        Response resposta =
                given()
                    .contentType(ContentType.JSON)
                    .body(dados_user1)
                .when()
                    .post(apiUrl)
                .then()
                    .extract().response();

        CriarUsuarioDTOResponse dadosResposta = resposta.as(CriarUsuarioDTOResponse.class);
        resposta_user1 = resposta.as(CriarUsuarioDTOResponse.class);

        assertEquals(201,resposta.getStatusCode());
        assertNotNull(dadosResposta.getId());
        assertEquals(dados_user1.getUsername(),dadosResposta.getUsername());
        assertEquals(dados_user1.getAvatar(),dadosResposta.getAvatar());
        assertEquals(0,dadosResposta.getFollowersList().size());
        assertEquals(0,dadosResposta.getFollowingList().size());
    }

    @Test
    @DisplayName("Deve gerar bad Request ao tentar criar um usuário com dados incompletos")
    @Order(2)
    public void failCreateUserTest(){


        Response resposta = given()
                .contentType(ContentType.JSON)
                .body(dados_incompletos_user2)
                .when()
                .post(apiUrl)
                .then()
                .extract().response();

        assertEquals(400,resposta.getStatusCode());
    }

    @Test
    @DisplayName("Deve dar erro de conflito ao tentar cadastrar usuario que ja existe no sistema")
    @Order(3)
    public void failCreateDuplicateUserTest(){
        userService.adicionarUsuario(dados_user3);


        var resposta = given()
                .contentType(ContentType.JSON)
                .body(dados_user3)
                .when()
                .post(apiUrl)
                .then()
                .extract().response();

        assertEquals(409,resposta.getStatusCode());

    }

    @Test
    @DisplayName("Should delete a user Successfully")
    public void deletarUsuarioPorId(){
        CriarUsuarioDTOResponse dados =userService.adicionarUsuario(dados_user3);


        var response =
                given()
                    .contentType(ContentType.JSON)
                    .pathParam("userId",dados.getId())
                .when()
                    .delete(apiUrl+ "/{userId}")
                .then()
                        .statusCode(204)
                                .extract().response();

        assertEquals(204,response.getStatusCode());
    }

    @Test
    @DisplayName("Deve conseguir editar um usuário")
    public void editarUsuarioPorId(){
        CriarUsuarioDTOResponse dados = userService.adicionarUsuario(dados_user3);

        dados_user3.setUsername("yhwach");
        dados_user3.setAvatar("yhwach.jpg");

        var response =
                given()
                        .contentType(ContentType.JSON)
                        .pathParam("userId",dados.getId())
                        .body(dados_user3)
                        .when()
                        .put(apiUrl+"/{userId}")
                        .then()
                        .extract().response();

        CriarUsuarioDTOResponse dadosResposta = response.as(CriarUsuarioDTOResponse.class);
        assertEquals(201,response.getStatusCode());
        assertEquals(dados_user3.getUsername(),dadosResposta.getUsername());
        assertEquals(dados_user3.getAvatar(),dadosResposta.getAvatar());

    }




}