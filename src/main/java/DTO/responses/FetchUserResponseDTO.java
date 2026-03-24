package DTO.responses;

import entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FetchUserResponseDTO {
    private long id;
    private String name;
    private String avatar;


    public static FetchUserResponseDTO mapearEntidadeDTO(UserEntity u){
        return FetchUserResponseDTO .builder()
                .id(u.getId())
                .name(u.getUsername())
                .avatar(u.getAvatar())
                .build();
    }
}
