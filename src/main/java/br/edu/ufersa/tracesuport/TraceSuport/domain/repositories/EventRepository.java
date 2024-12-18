package br.edu.ufersa.tracesuport.TraceSuport.domain.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Enterprise;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByEnterprise(Enterprise enterprise);
}
