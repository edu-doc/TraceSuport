package br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.CoordinatesDTO;

public class CoordenadaComDistancia {

    private final CoordinatesDTO coordenada;
    private final double distancia;

    public CoordenadaComDistancia(CoordinatesDTO coordenada, double distancia) {
        this.coordenada = coordenada;
        this.distancia = distancia;
    }

    public CoordinatesDTO getCoordenada() {
        return coordenada;
    }

    public double getDistancia() {
        return distancia;
    }
}
