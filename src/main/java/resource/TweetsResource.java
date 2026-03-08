package resource;


import DTO.requests.CriarTweetDTORequest;
import DTO.responses.CriarTweetDTOResponse;
import entity.TweetsEntity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;
import service.TweetsService;

import java.util.List;

@Path("/tweets")
@ApplicationScoped
@AllArgsConstructor
public class TweetsResource {
    final TweetsService tweetsService;

    @POST
    public RestResponse<?> criarTweetPorUsuario(CriarTweetDTORequest criarTweetDTORequest){
        try{
            if(criarTweetDTORequest.getText() == null || criarTweetDTORequest.getUserId() == null){
                return RestResponse.status(Response.Status.BAD_REQUEST,"Todos os campos devem estar preenchidos");
            }
            if(criarTweetDTORequest.getText().isBlank() ){
                return RestResponse.status(Response.Status.BAD_REQUEST,"Todos os campos devem ter conteúdo");
            }
            CriarTweetDTOResponse criarTweetDTOResponse = tweetsService.insertTweet(criarTweetDTORequest);
            return RestResponse.status(RestResponse.Status.CREATED,criarTweetDTOResponse);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }

    };
    @GET
    public RestResponse<List<TweetsEntity>> buscarTweetsUsuarios(){
        try{
            List<TweetsEntity> listaTweetsUsuarios = tweetsService.buscarTweetsUsuarios();
            return RestResponse.status(RestResponse.Status.OK,listaTweetsUsuarios);
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @GET
    @Path("/user/{userId}")
    public RestResponse<List<TweetsEntity>> buscarTweetsUsuariosPorId(@PathParam("userId") Long userIdSearched){
        try{
            List<TweetsEntity> listaTweetsUsuariosId = tweetsService.buscarTweetsUsuariosPorId(userIdSearched);
            return RestResponse.status(RestResponse.Status.OK,listaTweetsUsuariosId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
