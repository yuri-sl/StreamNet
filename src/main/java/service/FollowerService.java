package service;

import DTO.responses.FollowerDTO;
import entity.FollowerEntity;
import entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import repository.FollowerRepository;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@ApplicationScoped
public class FollowerService {
    private final UserService userService;
    private final FollowerRepository followerRepository;

    public List<FollowerDTO> buscarFollowersPorIdUser(long userId){
        UserEntity user = userService.verificarUsuarioExiste(userId);
        List<FollowerDTO> followerDTO = FollowerDTO.mapearEntidadeDTO(followerRepository.fetchFollowersByUserId(user));
        return followerDTO;
    }

    @Transactional
    public FollowerDTO seguirUsuarioPorIdUser(long userSeguirId, long userLogadoId){
        UserEntity usuarioASerSeguido = userService.verificarUsuarioExiste(userSeguirId);
        UserEntity usuarioLogado = userService.verificarUsuarioExiste(userLogadoId);

        FollowerEntity followerEntity = followerRepository.followNewUser(usuarioASerSeguido,usuarioLogado);
        FollowerDTO followerDTO = FollowerDTO.mapearEntidadeDTO(followerEntity);
        return  followerDTO;
    }

}
