package repository;

import entity.CommentEntity;
import entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@ApplicationScoped
public class CommentRepository implements PanacheRepository<CommentEntity> {
    @Inject
    UserRepository userRepository;

    public List<CommentEntity> fetchCommentsByUserId(long userId){
        UserEntity usuarioEncontrado = userRepository.buscarUsuarioPorId(userId);
        return find("WHERE postOwner = ?1",usuarioEncontrado).stream().toList();
    }
}
