package br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response;

import java.time.LocalDateTime;

import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponseDTO(User user) {
        setId(user.getId());
        setName(user.getName());
        setEmail(user.getEmail());
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(user.getUpdatedAt());
        setRole(user.getRoles().get(0).getName().toString());
    }
}

