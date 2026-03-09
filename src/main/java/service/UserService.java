package service;

import DTO.requests.CriarUsuarioDTORequest;
import DTO.responses.CriarUsuarioDTOResponse;
import DTO.responses.FetchUserResponseDTO;
import entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@ApplicationScoped
//Dá erro se eu tirar o AllArgsConstructor do UsuarioService. Pq q eu preciso dele?
public class UserService {


    final UserRepository userRepository;

    public UserEntity verificarUsuarioExiste(String username){
        UserEntity usuarioEncontrado = userRepository.buscarUsuarioPorNome(username);
        if(usuarioEncontrado == null) {
            throw new IllegalArgumentException("usuário não encontrado");
        }
        return usuarioEncontrado;
    }
    public UserEntity verificarUsuarioExiste(long userId){
        Optional<UserEntity> usuarioEncontrado = userRepository.findByIdOptional(userId);
        if(usuarioEncontrado.isEmpty()) {
            throw new IllegalArgumentException("usuário não encontrado");
        }
        return usuarioEncontrado.get();
    }

    public UserEntity fazerLoginSistema(String username){

        return verificarUsuarioExiste(username);
    }

    @Transactional
    public CriarUsuarioDTOResponse adicionarUsuario(CriarUsuarioDTORequest usuarioDTO){
       List<UserEntity> listaUsuarios  = userRepository.verificarUsuarioExiste(usuarioDTO);

       if(listaUsuarios.isEmpty()){
           UserEntity userCreated = UserEntity.builder()
                   .avatar(usuarioDTO.getAvatar())
                   .username(usuarioDTO.getUsername())
                   .build();
           userRepository.persist(userCreated);
           userRepository.flush();
           return CriarUsuarioDTOResponse.builder()
                   .id(userCreated.getId())
                   .avatar(userCreated.getAvatar())
                   .username(userCreated.getUsername())
                   .build();
       } else{
           throw new java.lang.RuntimeException("Usuario ja existente no sistema");
       }
    };

    public FetchUserResponseDTO buscarUsuarioPorNome(String username){
        UserEntity user =  this.userRepository.buscarUsuarioPorNome(username);
        FetchUserResponseDTO fetchUserResponseDTO = FetchUserResponseDTO.builder()
                .id(user.getId()).name(user.getUsername()).avatar(user.getAvatar()).build();
        return  fetchUserResponseDTO;
    }
}
