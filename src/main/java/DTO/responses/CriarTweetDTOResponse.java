package DTO.responses;


import entity.CommentEntity;
import entity.TweetsEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import repository.CommentRepository;
import repository.TweetsRepository;
import service.TweetsService;

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
