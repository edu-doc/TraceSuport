package br.edu.ufersa.tracesuport.TraceSuport.domain.services;

import java.util.List;
import org.springframework.stereotype.Service;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.EnterpriseRequest;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.EnterpriseResponse;
import br.edu.ufersa.tracesuport.TraceSuport.api.exceptions.IllegalFieldException;
import br.edu.ufersa.tracesuport.TraceSuport.domain.configuration.SecurityConfiguration;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Enterprise;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Role;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.User;
import br.edu.ufersa.tracesuport.TraceSuport.domain.enums.RolesEnum;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.EnterpriseRepository;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class EnterpriseService {
    private final EnterpriseRepository enterpriseRepository;

    private final UserRepository userRepository;

    public EnterpriseService(EnterpriseRepository enterpriseRepository, UserRepository userRepository) {
        this.enterpriseRepository = enterpriseRepository;
        this.userRepository = userRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public EnterpriseResponse create(EnterpriseRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new IllegalFieldException("Email já cadastrado", "email");
        });

        enterpriseRepository.findByCnpj(request.getCnpj()).ifPresent(enterprise -> {
            throw new IllegalFieldException("Cnpj já cadastrado", "cnpj");
        });

        User user = User.builder()
                .name(request.getOwnerName())
                .email(request.getEmail())
                .password(SecurityConfiguration.passwordEncoder().encode(request.getPassword()))
                .roles(List.of(Role.builder().name(RolesEnum.ROLE_CUSTOMER).build()))
                .build();

        user = userRepository.save(user);

        Enterprise enterprise = Enterprise.builder()
                .name(request.getEnterpriseName())
                .cnpj(request.getCnpj())
                .owner(user)
                .build();

        enterprise = enterpriseRepository.save(enterprise);

        return new EnterpriseResponse(enterprise);
    }

    public EnterpriseResponse find(Long id) {
        Enterprise enterprise = enterpriseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        return new EnterpriseResponse(enterprise);
    }

    public List<EnterpriseResponse> findAll() {
        return enterpriseRepository.findAll().stream().map(EnterpriseResponse::new).toList();
    }
}
