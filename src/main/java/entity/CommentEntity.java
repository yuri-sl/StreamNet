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

    @JoinColumn(name = "post_owner")
    @ManyToOne
    private UserEntity postOwner;

    @JoinColumn(name = "comment_owner")
    @ManyToOne
    private UserEntity commentUser;

    @JoinColumn(name = "post")
    @ManyToOne
    private TweetsEntity postCommented;

    @Column(name = "upvotes")
    @Builder.Default
    private int upvotes = 0;

    @Column(name = "downvotes")
    @Builder.Default
    private int downvotes = 0;

    @Column(name = "data_hora_publicacao")
    @Builder.Default
    private LocalDateTime publishingTime = LocalDateTime.now();

    @Column(name = "comment_text")
    private String commentText;


}
