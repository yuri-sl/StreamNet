package entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "TB_TWEETS")
@Builder
public class TweetsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text",nullable = false)
    private String text;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;

    @Column(name = "data_hora_publicacao", nullable = false)
    @Builder.Default
    private LocalDateTime publishing_time = LocalDateTime.now();

    @Column(name = "upvotes")
    @Builder.Default
    private int upvotes = 0;

    @Column(name = "downvotes")
    @Builder.Default
    private int downvotes = 0;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "comentarios")
    private CommentEntity listaComentarios;

}
