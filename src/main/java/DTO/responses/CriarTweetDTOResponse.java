package DTO.responses;


import entity.CommentEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApplicationScoped
public class CriarTweetDTOResponse {
    private long id;
    private String text;
    private CriarUsuarioDTOResponse criarUsuarioDTOResponse;
    private int upvotes;
    private int downvotes;
    private List<CommentEntity> listaComentarios;



}
