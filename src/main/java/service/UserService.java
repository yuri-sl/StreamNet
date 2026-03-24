package service;

import DTO.requests.CriarUsuarioDTORequest;
import DTO.responses.CriarUsuarioDTOResponse;
import DTO.responses.FetchUserResponseDTO;
import DTO.responses.GetUserDTO;
import entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.UserRepository;

import java.util.List;

@AllArgsConstructor
@ApplicationScoped
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
        return userRepository.findByIdOptional(userId)
                .orElseThrow(() -> new IllegalArgumentException("usuário não encontrado"));
    }

    public UserEntity fazerLoginSistema(String username){
        return verificarUsuarioExiste(username);
    }

    @Transactional
    public CriarUsuarioDTOResponse adicionarUsuario(CriarUsuarioDTORequest usuarioDTO){
        validarCampos(usuarioDTO);
        List<UserEntity> listaUsuarios = userRepository.verificarUsuarioExiste(usuarioDTO);

        if(listaUsuarios.isEmpty()){
            UserEntity userCreated = UserEntity.builder()
                    .avatar(usuarioDTO.getAvatar())
                    .username(usuarioDTO.getUsername())
                    .build();
            userRepository.persist(userCreated);
            userRepository.flush();
            return CriarUsuarioDTOResponse.mapearEntidadeDTO(userCreated);
        } else {
            throw new RuntimeException("Usuario ja existente no sistema");
        }
    }

    public FetchUserResponseDTO buscarUsuarioPorNome(String username){
        UserEntity user = userRepository.buscarUsuarioPorNome(username);
        return FetchUserResponseDTO.mapearEntidadeDTO(user);
    }

    public void validarCampos(CriarUsuarioDTORequest usuarioDTO){
        if(usuarioDTO.getUsername() == null || usuarioDTO.getAvatar() == null
                || usuarioDTO.getUsername().isBlank() || usuarioDTO.getAvatar().isBlank()){
            throw new IllegalArgumentException("Todos os campos devem estar preenchidos");
        }
    }

    @Transactional
    public CriarUsuarioDTOResponse updateUserOperation(long userId, CriarUsuarioDTORequest dados){
        validarCampos(dados);
        UserEntity usuario = verificarUsuarioExiste(userId);

        usuario.setUsername(dados.getUsername());
        usuario.setAvatar(dados.getAvatar());

        userRepository.persist(usuario);
        userRepository.flush();

        return CriarUsuarioDTOResponse.mapearEntidadeDTO(usuario);
    }

    @Transactional
    public void deleteUserById(long userId){
        userRepository.deleteById(userId);
    }

    public List<CriarUsuarioDTOResponse> listarTodosUsuarios(){
        return CriarUsuarioDTOResponse.mapearEntidadeDTO(userRepository.buscarTodosUsuarios());
    }

    public GetUserDTO listarUsuario(long userId){
        return GetUserDTO.mapearEntidadeDTO(verificarUsuarioExiste(userId));
    }
}