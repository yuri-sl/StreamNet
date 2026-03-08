package resource;

import DTO.requests.LoginRequest;
import DTO.responses.LoginResponse;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;

import java.time.Duration;
import java.util.Set;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @POST
    @Path("/login")
    @PermitAll
    public RestResponse<?> login(LoginRequest request){
        if(!"admin@email.com".equals(request.email()) || !"123".equals(request.password())){
            System.out.println(request.email());
            System.out.println(request.password());
            return RestResponse.status(Response.Status.UNAUTHORIZED,"Login não autorizado");
        }

        String token = Jwt.issuer("tweetero-api")
                .upn(request.email())
                .groups(Set.of("USER"))
                .expiresIn(Duration.ofHours(2))
                .sign();

        return RestResponse.status(RestResponse.Status.OK,new LoginResponse(token));


    }
}
