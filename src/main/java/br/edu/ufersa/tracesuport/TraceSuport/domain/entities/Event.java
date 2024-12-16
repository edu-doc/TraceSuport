package br.edu.ufersa.tracesuport.TraceSuport.domain.entities;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventDTO;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_events")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    @NotBlank
    private String district;

    @NotBlank
    private String address;

    @NotBlank
    private String number;

    @NotBlank
    private String phone;

    @NotBlank
    private String latitude;

    @NotBlank
    private String longitude;

    @NotBlank
    private String description;

    public Event (EventDTO eventDTO) {
        setId(eventDTO.getId());
        setName(eventDTO.getName());
        setCity(eventDTO.getCity());
        setDistrict(eventDTO.getDistrict());
        setAddress(eventDTO.getAddress());
        setNumber(eventDTO.getNumber());
        setPhone(eventDTO.getPhone());
        setLatitude(eventDTO.getLatitude());
        setLongitude(eventDTO.getLongitude());
        setDescription(eventDTO.getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
