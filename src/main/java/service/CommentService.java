package service;

import DTO.requests.CreateCommentDTORequest;
import DTO.responses.CreateCommentDTOResponse;
import DTO.responses.CriarTweetDTOResponse;
import DTO.responses.CriarUsuarioDTOResponse;
import entity.CommentEntity;
import entity.TweetsEntity;
import entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repository.CommentRepository;
import repository.TweetsRepository;

@ApplicationScoped
public class CommentService {
    @Inject
    CommentRepository commentRepository;
    @Inject
    TweetsService tweetsService;
    @Inject
    UserService userService;

    @Transactional
    public CreateCommentDTOResponse createNewComment(CreateCommentDTORequest dados){
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

        CreateCommentDTOResponse createCommentDTOResponse = CreateCommentDTOResponse.builder()
                .id(comentario.getId())
                .postOwner(CriarUsuarioDTOResponse.mapearEntidadeDTO(comentario.getPostOwner()))
                .commentUser(CriarUsuarioDTOResponse.mapearEntidadeDTO(comentario.getCommentUser()))
                .postCommented(mapearEntidadeDTO(comentario.getPostCommented()))
                .upvotes(comentario.getUpvotes())
                .downvotes(comentario.getDownvotes())
                .publishingTime(comentario.getPublishingTime())
                .commentText(comentario.getCommentText())
                .build();

        return createCommentDTOResponse;

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
    @Inject
    TweetsRepository tweetsRepository;

    public CriarTweetDTOResponse mapearEntidadeDTO(TweetsEntity tweet){
        return CriarTweetDTOResponse.builder()
                .id(tweet.getId())
                .text(tweet.getText())
                .criarUsuarioDTOResponse(CriarUsuarioDTOResponse.mapearEntidadeDTO(tweet.getUser()))
                .upvotes(tweet.getUpvotes())
                .downvotes(tweet.getDownvotes())
                .listaComentarios(commentRepository.fetchCommentsByUserId(tweet.getUser().getId()))
                .build();
    }


}
