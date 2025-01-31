package br.edu.ufersa.tracesuport.TraceSuport.api.DTO;

import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;
import br.edu.ufersa.tracesuport.TraceSuport.domain.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

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

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotBlank
    private String description;

    private Long enterpriseId;

    @NotNull
    private String StatusEnum;

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
        setEnterpriseId(event.getEnterprise().getId());
        setStatusEnum(String.valueOf(event.getStatus()));
    }

}
