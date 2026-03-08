package DTO.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class HealthDTOResponse {
    public String mensagem;
}
