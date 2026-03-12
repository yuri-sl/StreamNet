package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "TB_USERS")
@Entity
public class UserEntity {

    //Perguntar sobre essa geração de sequencia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "username",nullable = false)
    private String username;

//    @OneToMany(mappedBy = "follower")
//    @Builder.Default
//    private List<FollowerEntity> followerList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "followed")
//    @Builder.Default
//    private List<FollowerEntity> followedList = new ArrayList<>();

}
