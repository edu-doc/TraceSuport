package br.edu.ufersa.tracesuport.TraceSuport.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

}