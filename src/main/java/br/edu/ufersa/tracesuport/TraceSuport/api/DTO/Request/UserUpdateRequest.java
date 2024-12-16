package br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {    
    private String name;

    @Email
    private String email;
}
