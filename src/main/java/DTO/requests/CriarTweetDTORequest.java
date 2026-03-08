package DTO.requests;


import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@ApplicationScoped
public class CriarTweetDTORequest {
    public Long userId;
    public String text;
}
