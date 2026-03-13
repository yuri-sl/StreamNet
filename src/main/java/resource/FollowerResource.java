package resource;

import DTO.responses.FollowerDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;
import service.FollowerService;

import java.util.List;

@Path("/follower")
@AllArgsConstructor
@ApplicationScoped
public class FollowerResource {
    private final FollowerService followerService;

    @GET
    @Path("/{userId}")
    public RestResponse<?> getAllFollowersFromUser(@PathParam("userId") long userId){
        List<FollowerDTO> listaDTO = followerService.buscarFollowersPorIdUser(userId);
        return RestResponse.status(Response.Status.fromStatusCode(200),listaDTO);
    }

    @POST
    @Path("/{userId}")
    public RestResponse<?> followNewUser(@PathParam("userId")long userId,long userlogadoId){
        try{
            FollowerDTO followerDTO = followerService.seguirUsuarioPorIdUser(userId,userlogadoId);
            return RestResponse.status(Response.Status.fromStatusCode(201),followerDTO);
        } catch (Exception e) {
            return RestResponse.status(Response.Status.fromStatusCode(404),e.getMessage());
        }
    }


}
