package resource;

import DTO.requests.CriarUsuarioDTORequest;
import DTO.responses.CriarUsuarioDTOResponse;
import DTO.responses.FetchUserResponseDTO;
import DTO.responses.GetUserDTO;
import DTO.responses.HealthDTOResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.RestResponse;
import service.UserService;

import java.util.List;

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
            return RestResponse.status(Response.Status.CREATED, userService.adicionarUsuario(usuarioDTO));
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
            return RestResponse.status(Response.Status.CONFLICT, e.getMessage());
        }
    }

    @POST
    @Path("/auth")
    public RestResponse<?> fazerLogin(String username){
        try {
            return RestResponse.status(Response.Status.OK, userService.fazerLoginSistema(username));
        } catch (RuntimeException e) {
            return RestResponse.status(RestResponse.Status.BAD_REQUEST, e.getMessage());
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
    @Path("/listall")
    public RestResponse<List<CriarUsuarioDTOResponse>> buscarTodosUsuarios(){
        try {
            return RestResponse.status(Response.Status.OK, userService.listarTodosUsuarios());
        } catch (Exception e) {
            return RestResponse.status(Response.Status.NOT_FOUND, null);
        }
    }

    @GET
    @Path("/{userId}")
    public RestResponse<?> buscarUsuarioPorId(@PathParam("userId") long userId){
        try{
            return RestResponse.status(Response.Status.fromStatusCode(200),userService.listarUsuario(userId));
        } catch (Exception e) {
            return RestResponse.status(Response.Status.fromStatusCode(404),e.getMessage());
        }
    }

    @GET
    public RestResponse<HealthDTOResponse> teste(){
        HealthDTOResponse healthDTOResponse = HealthDTOResponse.builder()
                .mensagem("Estou retornando uma mensagem")
                .build();
        return RestResponse.status(RestResponse.Status.OK, healthDTOResponse);
    }

    @PUT
    @Path("/{userId}")
    public RestResponse<?> editarUsuario(@PathParam("userId") long userId, CriarUsuarioDTORequest dadosInput){
        try {
            return RestResponse.status(Response.Status.OK, userService.updateUserOperation(userId, dadosInput));
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    @DELETE
    @Path("/{userId}")
    public RestResponse<?> deletarUsuarioPorId(@PathParam("userId") long userId){
        userService.deleteUserById(userId);
        return RestResponse.status(Response.Status.NO_CONTENT);
    }
}