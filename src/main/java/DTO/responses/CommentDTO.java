package DTO.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentDTO {
    private long id;
    private CreateCommentDTOResponse postOwner;
    private CreateCommentDTOResponse commentUser;
    private CriarTweetDTOResponse postCommented;
    private int upvotes;
    private int downvotes;
    private LocalDateTime publishingTime;
    private String commentText;
}
