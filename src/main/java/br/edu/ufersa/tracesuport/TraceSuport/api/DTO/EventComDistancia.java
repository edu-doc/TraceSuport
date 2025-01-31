package br.edu.ufersa.tracesuport.TraceSuport.api.DTO;

import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;

public class EventComDistancia {
    private final Event event;
    private final double distancia;

    public EventComDistancia(Event event, double distancia) {
        this.event = event;
        this.distancia = distancia;
    }

    public Event getEvent() {
        return event;
    }

    public double getDistancia() {
        return distancia;
    }
}
