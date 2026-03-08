package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "TB_COMMENTS")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "dono_post")
    private UserEntity postOwner;

    @Column(name = "usuario_comenta")
    private UserEntity commentUser;

    @Column(name = "upvotes")
    @Builder.Default
    private int upvotes = 0;

    @Column(name = "downvotes")
    @Builder.Default
    private int downvotes = 0;

    @Column(name = "data_hora_publicacao")
    @Builder.Default
    private LocalDateTime publishingTime = LocalDateTime.now();


}
