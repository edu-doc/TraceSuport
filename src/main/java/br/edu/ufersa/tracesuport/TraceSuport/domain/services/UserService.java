package br.edu.ufersa.tracesuport.TraceSuport.domain.services;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.UserRequestDTO;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.UserUpdateRequest;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.UserResponseDTO;
import br.edu.ufersa.tracesuport.TraceSuport.api.exceptions.IllegalFieldException;
import br.edu.ufersa.tracesuport.TraceSuport.domain.configuration.SecurityConfiguration;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Enterprise;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Role;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.User;
import br.edu.ufersa.tracesuport.TraceSuport.domain.enums.RolesEnum;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.EnterpriseRepository;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.UserRepository;
import br.edu.ufersa.tracesuport.TraceSuport.domain.utils.FileUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    private final EnterpriseRepository enterpriseRepository;

    public UserService(UserRepository userRepository, EnterpriseRepository enterpriseRepository) {
        this.userRepository = userRepository;
        this.enterpriseRepository = enterpriseRepository;
    }

    public UserResponseDTO create(UserRequestDTO request) throws IOException {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new IllegalFieldException("Email já cadastrado", "email");
        });

        userRepository.findByCpf(request.getCpf()).ifPresent(user -> {
            throw new IllegalFieldException("CPF já cadastrado", "cpf");
        });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User authUser = (User) authentication.getPrincipal();

        Enterprise enterprise = enterpriseRepository.findByOwner(authUser).orElseThrow(() -> {
            throw new IllegalFieldException("Empresa não encontrada", "enterpriseId");
        });

        String filePath = FileUtils.saveFile("/user_photos", request.getPhoto());

        User user = User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .cpf(request.getCpf())
                        .password(SecurityConfiguration.passwordEncoder().encode(request.getPassword()))
                        .photoPath(filePath)
                        .roles(List.of(Role.builder().name(RolesEnum.ROLE_USER).build()))
                        .dependentEnterprise(enterprise)
                        .build();

        user = userRepository.save(user);

        return new UserResponseDTO(user);
    }

    public UserResponseDTO update(Long id, UserUpdateRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new IllegalArgumentException("Email já cadastrado");
        });
        
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        user.setName(request.getName().isBlank() ? user.getName() : request.getName());
        user.setEmail(request.getEmail().isBlank() ? user.getEmail() : request.getEmail());
        user = userRepository.save(user);

        return new UserResponseDTO(user);
    }

    public UserResponseDTO getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new UserResponseDTO(user);
    }

    public void delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User authUser = (User) authentication.getPrincipal();

        Enterprise enterprise = enterpriseRepository.findByOwner(authUser).orElseThrow(() -> {
            throw new IllegalFieldException("Empresa não encontrada", "enterpriseId");
        });

        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (user.getRoles().stream().anyMatch(role -> Arrays.asList(RolesEnum.ROLE_ADMIN, RolesEnum.ROLE_CUSTOMER).contains(role.getName()))) {
            throw new IllegalArgumentException("Não é possível deletar esse usuario");
        }

        if (authUser.getId() == user.getId()) {
            throw new IllegalArgumentException("Não é possível deletar o próprio usuário");
        }

        if (user.getDependentEnterprise().getId() != enterprise.getId()) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
    }
}

