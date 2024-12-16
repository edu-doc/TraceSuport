package br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;

    private String refreshToken;

    private UserResponseDTO user;
}
