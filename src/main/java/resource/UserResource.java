package resource;

import DTO.requests.CriarTweetDTORequest;
import DTO.requests.CriarUsuarioDTORequest;
import DTO.responses.CriarTweetDTOResponse;
import DTO.responses.CriarUsuarioDTOResponse;
import DTO.responses.FetchUserResponseDTO;
import DTO.responses.HealthDTOResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.RestResponse;
import service.UserService;

@AllArgsConstructor


@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UserResource {
    final UserService userService;

    @POST
    public RestResponse<?> criarUsuarioNovo(@RequestBody CriarUsuarioDTORequest usuarioDTO){
        try {
            return RestResponse.status(Response.Status.CREATED,userService.adicionarUsuario(usuarioDTO));
        }catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.BAD_REQUEST,e.getMessage());
        }
        catch (RuntimeException e) {
            return RestResponse.status(Response.Status.CONFLICT,e.getMessage());
        }
    };
    @POST
    @Path("/auth")
    public RestResponse<?> fazerLogin(String username){
        try{
            return  RestResponse.status(Response.Status.fromStatusCode(200), userService.fazerLoginSistema(username));
        } catch (RuntimeException e) {
            return RestResponse.status(RestResponse.Status.BAD_REQUEST,e.getMessage());
        }
    }
    @GET
    @Path("/{username}")
    public RestResponse<FetchUserResponseDTO> buscarUsuarioNome(@PathParam("username") String username){
        try{
            FetchUserResponseDTO respostaUsuarioEncontradoNome =  userService.buscarUsuarioPorNome(username);
            return  RestResponse.status(RestResponse.Status.OK,respostaUsuarioEncontradoNome);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    public RestResponse<HealthDTOResponse> teste(){
        HealthDTOResponse healthDTOResponse = HealthDTOResponse.builder()
                .mensagem("Estou retornando uma mensagem")
                .build();
        return RestResponse.status(RestResponse.Status.OK,healthDTOResponse);
    }

    @PUT
    public RestResponse<?> editarUsuario(CriarUsuarioDTORequest dadosInput){
        try{
            return RestResponse.status(Response.Status.fromStatusCode(201),userService.updateUserOperation(dadosInput));
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.BAD_REQUEST,e.getMessage());
        }

    }

    @DELETE
    @Path("/{userId}")
    public RestResponse<?> deletarUsuarioPorId(@PathParam("userId") long userId){
        try{
            userService.deleteUserById(userId);
            return RestResponse.status(Response.Status.fromStatusCode(204),"Deleted");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
