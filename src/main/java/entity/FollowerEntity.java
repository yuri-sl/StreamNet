package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TB_FOLLOWERS")
public class FollowerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "FOLLOWER",nullable = false)
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private UserEntity follower;

    @JoinColumn(name = "FOLLOWED",nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UserEntity followed;

    @Column(name = "FOLLOWED_TIME",nullable = false)
    @Builder.Default
    private LocalDateTime dateFollowed = LocalDateTime.now();

    @Column(name = "IS_BLOCKED")
    @Builder.Default
    private boolean isBlocked = false;

}
