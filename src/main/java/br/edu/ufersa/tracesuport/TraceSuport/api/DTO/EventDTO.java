package br.edu.ufersa.tracesuport.TraceSuport.api.DTO;

import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    Long id;

    String name;

    String city;

    String district;

    String address;

    String number;

    String phone;

    String latitude;

    String longitude;

    String description;

    public EventDTO(Event event) {
        setId(event.getId());
        setName(event.getName());
        setCity(event.getCity());
        setDistrict(event.getDistrict());
        setAddress(event.getAddress());
        setNumber(event.getNumber());
        setPhone(event.getPhone());
        setLatitude(event.getLatitude());
        setLongitude(event.getLongitude());
        setDescription(event.getDescription());
    }

}
