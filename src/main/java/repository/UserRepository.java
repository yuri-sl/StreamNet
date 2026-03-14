package repository;

import DTO.requests.CriarUsuarioDTORequest;
import entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {
    public List<UserEntity> verificarUsuarioExiste(CriarUsuarioDTORequest criarUsuarioDTORequest){
        return find("where username = ?1", criarUsuarioDTORequest.getUsername()).stream().toList();
    }

    public UserEntity buscarUsuarioPorId(Long userId){
        return find("where id = ?1",userId).stream().toList().getFirst();
    }

    public UserEntity buscarUsuarioPorNome(String username){
        return find("where username = ?1",username).firstResult();
    }
}
