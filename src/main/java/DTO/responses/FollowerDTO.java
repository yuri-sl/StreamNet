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
    private LocalDateTime dateFollowed;
    private boolean isBlocked;

    private long followerId;
    private String followerUsername;

    private Long followedId;
    private String followedUsername;


    public static FollowerDTO mapearEntidadeDTO(FollowerEntity followerE){
        return FollowerDTO.builder()
                .id(followerE.getId())
                .dateFollowed(followerE.getDateFollowed())
                .isBlocked(followerE.isBlocked())
                .followerId(followerE.getFollower().getId())
                .followerUsername(followerE.getFollowed().getUsername())
                .followedId(followerE.getFollowed().getId())
                .followedUsername(followerE.getFollowed().getUsername())
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
