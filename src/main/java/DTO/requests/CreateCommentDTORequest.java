package DTO.requests;

import entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateCommentDTORequest {
    private long postOwnerId;
    private long commentOwnerId;
    private long postId;
    private String commentText;

}
