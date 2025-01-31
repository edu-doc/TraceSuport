package br.edu.ufersa.tracesuport.TraceSuport.domain.entities;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventDTO;
import br.edu.ufersa.tracesuport.TraceSuport.domain.enums.StatusEnum;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "enterprise_id", nullable = false, referencedColumnName = "id")
    private Enterprise enterprise;

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
        setStatus(eventDTO.getStatus());
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
