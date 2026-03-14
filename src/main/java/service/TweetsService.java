package service;


import DTO.requests.CriarTweetDTORequest;
import DTO.responses.CriarTweetDTOResponse;
import DTO.responses.CriarUsuarioDTOResponse;
import entity.TweetsEntity;
import entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import repository.TweetsRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@ApplicationScoped
public class TweetsService {

    final TweetsRepository tweetsRepository;
    final UserRepository userRepository;

    @Transactional
    public CriarTweetDTOResponse insertTweet(CriarTweetDTORequest criarTweetDTORequest){
        try{
            UserEntity usuarioEncontrado = userRepository.buscarUsuarioPorId(criarTweetDTORequest.getUserId());

            CriarUsuarioDTOResponse dtoUsuarioEncontrado  = CriarUsuarioDTOResponse.builder()
                    .id(usuarioEncontrado.getId())
                    .avatar(usuarioEncontrado.getAvatar())
                    .username(usuarioEncontrado.getUsername())
                    .build();


            TweetsEntity tweetCriado = TweetsEntity.builder()
                    .text(criarTweetDTORequest.getText())
                    .user(usuarioEncontrado)
                    .build();
            tweetsRepository.persist(tweetCriado);
            tweetsRepository.flush();

            return CriarTweetDTOResponse.builder()
                    .id(tweetCriado.getId())
                    .text(tweetCriado.getText())
                    .criarUsuarioDTOResponse(dtoUsuarioEncontrado)
                    .listaComentarios(new ArrayList<>())
                    .build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public List<TweetsEntity> buscarTweetsUsuarios(){
        return tweetsRepository.listAll();
    }
    public List<TweetsEntity> buscarTweetsUsuariosPorId(Long idUsuario){
        return tweetsRepository.fetchTweetsByUserId(idUsuario);
    }
    public TweetsEntity verificarTweetExiste(long postId){
        Optional<TweetsEntity> tweetEncontrado = tweetsRepository.findByIdOptional(postId);
        if(tweetEncontrado.isEmpty()){
            throw new IllegalArgumentException("Não foi encontrado tweet valido");
        }
        return tweetEncontrado.get();
    }



}
