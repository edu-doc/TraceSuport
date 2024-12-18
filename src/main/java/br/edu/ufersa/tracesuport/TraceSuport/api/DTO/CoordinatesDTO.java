package br.edu.ufersa.tracesuport.TraceSuport.api.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoordinatesDTO {

    private String latitude;
    private String longitude;

    public CoordinatesDTO(String latitude, String longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }
}
