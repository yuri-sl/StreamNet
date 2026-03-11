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
public class CriarUsuarioDTOResponse {
    private Long id;
    private String avatar;
    private String username;
    @Builder.Default
    private List<FollowerEntity> followersList = new ArrayList<>();
    @Builder.Default
    private List<FollowerEntity> followingList = new ArrayList<>();
}
