package DTO.responses;

import entity.FollowerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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


    public static FollowerDTO mapearEntidadeDTO(FollowerEntity followerE){
        return FollowerDTO.builder()
                .id(followerE.getId())
                .follower( CriarUsuarioDTOResponse.mapearEntidadeDTO(followerE.getFollower()))
                .followed(CriarUsuarioDTOResponse.mapearEntidadeDTO(followerE.getFollowed()))
                .dateFollowed(followerE.getDateFollowed())
                .isBlocked(followerE.isBlocked())
                .build();
    }
    public static List<FollowerDTO> mapearEntidadeDTO(List<FollowerEntity> listaFollowers){
        List<FollowerDTO> listaDTO = new ArrayList<>();
        FollowerDTO followerDTO = new FollowerDTO();
        for(FollowerEntity f : listaFollowers){
            followerDTO =followerDTO.mapearEntidadeDTO(f);
            listaDTO.add(followerDTO);
        }
        return listaDTO;
    }

}
