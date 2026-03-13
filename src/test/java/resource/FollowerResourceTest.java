package resource;

import DTO.requests.CreateCommentDTORequest;
import DTO.requests.CriarTweetDTORequest;
import DTO.requests.CriarUsuarioDTORequest;
import DTO.responses.CriarTweetDTOResponse;
import DTO.responses.CriarUsuarioDTOResponse;
import entity.UserEntity;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.TweetsRepository;
import service.TweetsService;
import service.UserService;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.reflections.Reflections.log;

@QuarkusTest
class FollowerResourceTest {

    @Inject
    UserService userService;

    UserEntity user;
    CriarUsuarioDTORequest criarUsuarioDTORequest;
    CriarUsuarioDTOResponse usuario_1,usuario_2;

    CriarTweetDTORequest criarTweetDTORequest;
    String tweetText;
    CriarTweetDTOResponse criarTweetDTOResponse;

    CreateCommentDTORequest createCommentDTORequest;
    String commentText;
    @Inject
    TweetsRepository tweetsRepository;
    @Inject
    TweetsService tweetsService;

    @BeforeEach
    public void setUp(){
        criarUsuarioDTORequest = CriarUsuarioDTORequest.builder()
                .username("kefla")
                .avatar("cute-kefla.jpg")
                .build();
        usuario_1 = userService.adicionarUsuario(criarUsuarioDTORequest);

        criarUsuarioDTORequest = CriarUsuarioDTORequest.builder()
                .username("goku")
                .avatar("gokuu.jpg")
                .build();
        usuario_2 = userService.adicionarUsuario(criarUsuarioDTORequest);

        tweetText = "Teste de novo tweet";
        criarTweetDTORequest = CriarTweetDTORequest.builder().userId(usuario_1.getId()).text(tweetText).build();
        criarTweetDTOResponse = tweetsService.insertTweet(criarTweetDTORequest);
    }

    @Test
    @DisplayName("Should follow an user successfully")
    void followNewUser() {
        long usuarioParaSeguirId = usuario_1.getId();
        long usuarioLogadoId = usuario_2.getId();


        var Response = given()
                .contentType(ContentType.JSON)
                .body(usuarioLogadoId)
                .when()
                .post("/follower/"+usuarioParaSeguirId)
                .then()
                .extract().response();
        System.out.println(Response.asString());

        assertEquals(201,Response.getStatusCode());


    }
}