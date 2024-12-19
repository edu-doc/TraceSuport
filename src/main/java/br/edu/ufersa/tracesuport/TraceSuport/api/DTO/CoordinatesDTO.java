package br.edu.ufersa.tracesuport.TraceSuport.api.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoordinatesDTO {

    private Double latitude;
    private Double longitude;

    public CoordinatesDTO(Double latitude, Double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }

}
