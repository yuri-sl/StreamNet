package records

import DTO.requests.CreateCommentDTORequest
import entity.TweetsEntity
import entity.UserEntity
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@AllArgsConstructor
@NoArgsConstructor
@Data
record CommentValidationContext(UserEntity postOwner,UserEntity commentOwner, TweetsEntity tweets) {


    public CommentValidationContext validarContextoComentario(CreateCommentDTORequest dados) {
        UserEntity postOwnerEncontrado = userService.verificarUsuarioExiste(dados.getPostOwnerId());
        UserEntity commentOwnerEncontrado = userService.verificarUsuarioExiste(dados.getCommentOwnerId());
        TweetsEntity tweetEncontrado = tweetsService.verificarTweetExiste(dados.getPostId());

        return new CommentValidationContext(
                postOwnerEncontrado,
                commentOwnerEncontrado,
                tweetEncontrado
        );
    }
}