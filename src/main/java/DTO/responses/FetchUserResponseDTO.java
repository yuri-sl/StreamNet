package DTO.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FetchUserResponseDTO {
    private long id;
    private String name;
    private String avatar;
}
