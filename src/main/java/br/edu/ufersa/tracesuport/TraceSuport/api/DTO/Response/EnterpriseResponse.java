package br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response;

import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Enterprise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseResponse {
    private Long id;

    private String name;

    private String cnpj;

    private UserResponseDTO user;

    public EnterpriseResponse(Enterprise enterprise) {
        this.id = enterprise.getId();
        this.name = enterprise.getName();
        this.cnpj = enterprise.getCnpj();
        this.user = new UserResponseDTO(enterprise.getUser());
    }
}
