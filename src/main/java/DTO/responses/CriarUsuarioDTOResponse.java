package DTO.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CriarUsuarioDTOResponse {
    private Long id;
    private String avatar;
    private String username;
}
