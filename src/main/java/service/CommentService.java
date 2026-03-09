package service;

import DTO.requests.CreateCommentDTORequest;
import entity.CommentEntity;
import entity.TweetsEntity;
import entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repository.CommentRepository;

@ApplicationScoped
public class CommentService {
    @Inject
    CommentRepository commentRepository;
    @Inject
    TweetsService tweetsService;
    @Inject
    UserService userService;

    @Transactional
    public CommentEntity createNewComment(CreateCommentDTORequest dados){
        UserEntity postOwnerEncontrado = userService.verificarUsuarioExiste(dados.getPostOwnerId());
        UserEntity commentOwnerEncontrado = userService.verificarUsuarioExiste(dados.getCommentOwnerId());
        TweetsEntity tweetEncontrado = tweetsService.verificarTweetExiste(dados.getPostId());

        CommentEntity comentario = CommentEntity.builder()
                .postOwner(postOwnerEncontrado)
                .commentUser(commentOwnerEncontrado)
                .postCommented(tweetEncontrado)
                .commentText(dados.getCommentText())
                .build();

        commentRepository.persist(comentario);
        commentRepository.flush();

        return comentario;

    }

    @Transactional
    public void editComment(CreateCommentDTORequest dados){
        UserEntity postOwnerEncontrado = userService.verificarUsuarioExiste(dados.getPostOwnerId());
        UserEntity commentOwnerEncontrado = userService.verificarUsuarioExiste(dados.getCommentOwnerId());
        TweetsEntity tweetEncontrado = tweetsService.verificarTweetExiste(dados.getPostId());

        CommentEntity comentario = CommentEntity.builder()
                .postOwner(postOwnerEncontrado)
                .commentUser(commentOwnerEncontrado)
                .postCommented(tweetEncontrado)
                .commentText(dados.getCommentText())
                .build();

        commentRepository.persist(comentario);
    }


}
