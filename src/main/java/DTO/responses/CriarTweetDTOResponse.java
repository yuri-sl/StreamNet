package DTO.responses;


import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApplicationScoped
public class CriarTweetDTOResponse {
    public long id;
    public String text;
    public CriarUsuarioDTOResponse criarUsuarioDTOResponse;

}
