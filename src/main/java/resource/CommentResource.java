package resource;

import DTO.requests.CreateCommentDTORequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestResponse;
import service.CommentService;

@Path("/post/{postId}/comment")
public class CommentResource {
    @Inject
    CommentService commentService;

    @POST
    public RestResponse<?> comentarEmPost(CreateCommentDTORequest dados){
        try{
            return RestResponse.status(RestResponse.Status.fromStatusCode(201),commentService.createNewComment(dados));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
