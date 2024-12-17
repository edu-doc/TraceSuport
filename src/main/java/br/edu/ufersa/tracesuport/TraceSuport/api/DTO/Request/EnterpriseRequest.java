package br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseRequest {
    @NotBlank(message = "O Nome da empresa é obrigatório")
    private String enterpriseName;

    @NotBlank(message = "O CNPJ é obrigatório")
    private String cnpj;

    @NotBlank(message = "O Email é obrigatório")
    @Email(message = "O Email é inválido")
    private String email;

    @NotBlank(message = "O Nome do proprietário é obrigatório")
    private String ownerName;

    @NotBlank(message = "A Senha é obrigatória")
    @Size(min = 6, message = "A Senha deve ter no mínimo 6 caracteres") 
    private String password;
}
