package service;

import DTO.requests.CriarUsuarioDTORequest;
import DTO.responses.CriarUsuarioDTOResponse;
import DTO.responses.FetchUserResponseDTO;
import entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;
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
        this.validarCampos(usuarioDTO);
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
    public void validarCampos(CriarUsuarioDTORequest usuarioDTO){
        if(usuarioDTO.getUsername() == null || usuarioDTO.getAvatar() == null){
            throw new IllegalArgumentException("Todos os campos devem estar preenchidos");
        }
        if(usuarioDTO.getUsername().isBlank() || usuarioDTO.getAvatar().isBlank() ){
            throw new IllegalArgumentException("Todos os campos devem estar preenchidos");
        }
    }

    @Transactional
    public CriarUsuarioDTOResponse updateUserOperation(CriarUsuarioDTORequest dados){
        validarCampos(dados);
        UserEntity usuario =userRepository.buscarUsuarioPorNome(dados.getUsername());

        usuario.setUsername(dados.getUsername());
        usuario.setAvatar(dados.getAvatar());

        userRepository.persist(usuario);
        userRepository.flush();

        return CriarUsuarioDTOResponse.builder()
                .id(usuario.getId())
                .avatar(usuario.getAvatar())
                .username(usuario.getUsername())
                .followersList(usuario.getFollowerList())
                .followingList(usuario.getFollowedList())
                .build();
    }

    @Transactional
    public UserEntity deleteUserById(long userId){
        this.userRepository.deleteById(userId);

    }

}
