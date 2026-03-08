package repository;

import entity.TweetsEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
@ApplicationScoped

public class TweetsRepository implements PanacheRepository<TweetsEntity> {

    public List<TweetsEntity> fetchTweetsByUserId(Long id){
        return find("user.id = ?1",id).list();
    }
}
