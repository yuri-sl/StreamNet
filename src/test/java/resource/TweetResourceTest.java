package resource;


import DTO.requests.CriarTweetDTORequest;
import DTO.requests.CriarUsuarioDTORequest;
import DTO.responses.CriarTweetDTOResponse;
import DTO.responses.CriarUsuarioDTOResponse;
import entity.UserEntity;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.TweetsService;
import service.UserService;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;


import java.net.URI;


@QuarkusTest
class TweetResourceTest {
    @Inject
    TweetsService tweetsService;

    @Inject
    UserService userService;

    @TestHTTPResource("/tweets")
    URI apiUrl;

    UserEntity user;
    CriarUsuarioDTORequest criarUsuarioDTORequest;
    CriarUsuarioDTOResponse usuario_1;

    CriarTweetDTORequest criarTweetDTORequest;
    String tweetText;

    @BeforeEach
    void setUp(){
        criarUsuarioDTORequest = CriarUsuarioDTORequest.builder()
                .username("kefla")
                .avatar("cute-kefla.jpg")
                .build();
       usuario_1 = userService.adicionarUsuario(criarUsuarioDTORequest);
    }

    @Test
    @DisplayName("Should create new Tweet using user_1")
    public void createNewTweet(){
        tweetText = "Teste de novo tweet";
        criarTweetDTORequest = CriarTweetDTORequest.builder().userId(usuario_1.getId()).text(tweetText).build();

        var response =
                given().body(criarTweetDTORequest)
                    .contentType(ContentType.JSON)
                .when()
                    .post(apiUrl)
                .then()
                    .extract().response();

        CriarTweetDTOResponse dadosResposta = response.as(CriarTweetDTOResponse.class);

        assertEquals(201,response.getStatusCode());
        assertNotNull(dadosResposta);
        assertEquals(1,dadosResposta.getId());
        assertEquals(0,dadosResposta.getUpvotes());
        assertEquals(0,dadosResposta.getDownvotes());
        assertEquals(0,dadosResposta.getListaComentarios().size());
        assertEquals(tweetText,dadosResposta.getText());



    }


}
