package br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "O nome não pode ser vazio")
    private String name;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "O CPF não pode ser vazio")
    @CPF(message = "O CPF deve ser válido")
    private String cpf;


    @NotBlank(message = "A senha não pode ser vazia")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password;

    @NotNull(message = "O funcionario deve ter uma foto")
    private MultipartFile photo;
}

