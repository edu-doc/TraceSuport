package br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response;

import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;

public class CoordenadaComDistancia {

    private final Event coordenada;
    private final double distancia;

    public CoordenadaComDistancia(Event coordenada, double distancia) {
        this.coordenada = coordenada;
        this.distancia = distancia;
    }

    public Event getCoordenada() {
        return coordenada;
    }

    public double getDistancia() {
        return distancia;
    }
}
