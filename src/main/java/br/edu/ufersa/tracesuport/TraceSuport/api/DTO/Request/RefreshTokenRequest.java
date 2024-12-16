package br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
    @NotBlank(message = "O token não pode ser vazio")
    private String refreshToken;   
}
