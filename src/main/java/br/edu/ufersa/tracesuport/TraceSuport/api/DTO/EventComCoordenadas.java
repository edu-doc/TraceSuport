package br.edu.ufersa.tracesuport.TraceSuport.api.DTO;

import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;

public class EventComCoordenadas {
    private final Event event;
    private final CoordinatesDTO coordenadas;

    public EventComCoordenadas(Event event, CoordinatesDTO coordenadas) {
        this.event = event;
        this.coordenadas = coordenadas;
    }

    public Event getEvent() {
        return event;
    }

    public CoordinatesDTO getCoordenadas() {
        return coordenadas;
    }
}

