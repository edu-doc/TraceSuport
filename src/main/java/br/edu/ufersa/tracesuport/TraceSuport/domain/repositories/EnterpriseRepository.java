package br.edu.ufersa.tracesuport.TraceSuport.domain.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Enterprise;

public interface 
EnterpriseRepository extends JpaRepository<Enterprise, Long> {
    Optional<Enterprise> findByid(long id);
}
