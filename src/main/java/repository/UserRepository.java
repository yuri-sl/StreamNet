package repository;

import DTO.requests.CriarUsuarioDTORequest;
import entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    public List<UserEntity> verificarUsuarioExiste(CriarUsuarioDTORequest criarUsuarioDTORequest){
        return find("username", criarUsuarioDTORequest.getUsername()).stream().toList();
    }

    public UserEntity buscarUsuarioPorId(Long userId){
        return findByIdOptional(userId)
                .orElseThrow(() -> new IllegalArgumentException("usuário não encontrado"));
    }

    public UserEntity buscarUsuarioPorNome(String username){
        return find("username", username).firstResult();
    }

    public List<UserEntity> buscarTodosUsuarios(){
        return listAll();
    }
}