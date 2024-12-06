package br.edu.ufersa.tracesuport.TraceSuport.api.DTO;

import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;

public class EventDTO {

    Long id;
    String name;

    public EventDTO(Event event) {
        this.id = event.getId();
        this.name = event.getName();
    }
}
