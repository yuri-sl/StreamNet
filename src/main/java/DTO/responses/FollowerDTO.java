package DTO.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FollowerDTO {
    private long id;
    private CriarUsuarioDTOResponse follower;
    private CriarUsuarioDTOResponse followed;
    private LocalDateTime dateFollowed;
    private boolean isBlocked;

}
