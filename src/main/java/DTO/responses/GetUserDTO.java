package DTO.responses;

import entity.FollowerEntity;
import entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDTO {
    private long id;
    private String avatar;
    private String username;
    private List<UserDTOBasico> followersList;
    private List<UserDTOBasico> followingList;


    public static List<UserDTOBasico> mapearSeguidores(List<FollowerEntity> lista){
        return lista.stream()
                .map(relacao -> UserDTOBasico.builder()
                        .id(relacao.getFollower().getId())
                        .avatar(relacao.getFollower().getAvatar())
                        .username(relacao.getFollower().getUsername())
                        .build())
                .toList();
    }
    public static List<UserDTOBasico> mapearSeguindo(List<FollowerEntity> lista){
        return lista.stream()
                .map(relacao-> UserDTOBasico.builder()
                        .id(relacao.getFollowed().getId())
                        .avatar(relacao.getFollowed().getAvatar())
                        .username(relacao.getFollowed().getUsername())
                        .build())
                .toList();
    }

    public static GetUserDTO mapearEntidadeDTO(UserEntity u){
        return GetUserDTO.builder()
                .id(u.getId())
                .avatar(u.getAvatar())
                .username(u.getUsername())
                .followersList(mapearSeguidores(u.getFollowerList()))
                .followingList(mapearSeguindo(u.getFollowedList()))
                .build();
    }
}
