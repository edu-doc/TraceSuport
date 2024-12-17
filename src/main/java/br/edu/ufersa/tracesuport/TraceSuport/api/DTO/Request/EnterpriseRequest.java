package br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseRequest {
    private String enterpriseName;

    private String cnpj;

    private String email;

    private String ownerName;

    private String password;
}
