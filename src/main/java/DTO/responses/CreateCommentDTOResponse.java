package DTO.responses;

import entity.TweetsEntity;
import entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateCommentDTOResponse {
    private long id;
    private CriarUsuarioDTOResponse postOwner;
    private CriarUsuarioDTOResponse commentUser;
    private CriarTweetDTOResponse postCommented;
    private int upvotes;
    private int downvotes;
    private LocalDateTime publishingTime;
    private String commentText;

}
