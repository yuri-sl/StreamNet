package repository;

import entity.FollowerEntity;
import entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<FollowerEntity> {

    public List<FollowerEntity> fetchFollowersByUserId(UserEntity usuario){
        return find("WHERE followed = ?1",usuario).stream().toList();
    }

    public FollowerEntity followNewUser(UserEntity userToBeFollowed,UserEntity userLogged){

        FollowerEntity followerEntity = FollowerEntity.builder()
                .follower(userLogged)
                .followed(userToBeFollowed)
                .dateFollowed(LocalDateTime.now())
                .isBlocked(false)
                .build();

        this.persist(followerEntity);
        this.flush();

        return followerEntity;
    }

}
