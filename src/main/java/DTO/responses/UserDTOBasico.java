package DTO.responses;


import entity.FollowerEntity;
import entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class UserDTOBasico {
    private Long id;
    private String avatar;
    private String username;

    public static CriarUsuarioDTOResponse mapearEntidadeDTO(UserEntity u){
        return CriarUsuarioDTOResponse.builder()
                .id(u.getId())
                .avatar(u.getAvatar())
                .username(u.getUsername())
                .build();
    }
}
