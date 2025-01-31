package br.edu.ufersa.tracesuport.TraceSuport.domain.services;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.CoordinatesDTO;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventComCoordenadas;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventComDistancia;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventDTO;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventUpdateDTO;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Enterprise;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.User;
import br.edu.ufersa.tracesuport.TraceSuport.domain.enums.StatusEnum;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.EnterpriseRepository;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.EventRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final EnterpriseRepository enterpriseRepository;

    public EventService (EventRepository repo, EnterpriseRepository enterpriseRepository){
        this.eventRepository = repo;
        this.enterpriseRepository = enterpriseRepository;
    }

    public List<EventDTO> listar() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Optional<Enterprise> optionalEnterprise = enterpriseRepository.findByOwner(user);

        Enterprise enterprise;

        if (optionalEnterprise.isPresent()) {
            enterprise = optionalEnterprise.get();
        } else {
            enterprise = user.getDependentEnterprise();
        }

        return eventRepository.findByEnterprise(enterprise)
                .stream()
                .map(event -> new EventDTO(event))
                .collect(Collectors.toList());
    }

    public EventDTO criar(EventDTO dto) throws DataIntegrityViolationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Enterprise enterprise = enterpriseRepository.findByOwner(user).get();

        Event event = new Event(dto);
        event.setEnterprise(enterprise);
        event.setStatus(StatusEnum.OPEN);

        return new EventDTO(eventRepository.save(event));
    }

    public EventDTO get(Long id) {
        return eventRepository.findById(id)
                .map(event -> new EventDTO(event))
                .orElseThrow(() -> new IllegalArgumentException("chamado não encontrado"));
    }

    public EventDTO atualizar(EventUpdateDTO dto, Long id) throws IllegalArgumentException {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("chamado não encontrado com esse ID"));

        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setLatitude(dto.getLatitude());
        event.setLongitude(dto.getLongitude());
        event.setCity(dto.getCity());
        event.setDistrict(dto.getDistrict());
        event.setAddress(dto.getAddress());
        event.setNumber(dto.getNumber());
        event.setPhone(dto.getPhone());
        event.setStatus(StatusEnum.valueOf(dto.getStatus()));

        return new EventDTO(eventRepository.save(event));
    }

    public CoordinatesDTO obterCoordenadas(Long id){
        return eventRepository.findById(id)
                .map(event -> new CoordinatesDTO(event.getLatitude(), event.getLongitude()))
                .orElseThrow(() -> new IllegalArgumentException("não possui chamado com esse ID"));

    }

    public EventDTO deletar(Long id) throws DataIntegrityViolationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Optional<Enterprise> optionalEnterprise = enterpriseRepository.findByOwner(user);

        Enterprise enterprise;

        if (optionalEnterprise.isPresent()) {
            enterprise = optionalEnterprise.get();
        } else {
            enterprise = user.getDependentEnterprise();
        }

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("chamado não encontrado com esse ID"));

        if (!event.getEnterprise().equals(enterprise)) {
            throw new IllegalArgumentException("chamado não encontrado com esse ID");
        }

        eventRepository.delete(event);

        return new EventDTO(event);
    }

    public List<EventDTO> maisProximo(Double lat, Double log) throws IllegalArgumentException {

        CoordinatesDTO coordenadaReferencia = new CoordinatesDTO(lat, log);

        List<Event> eventos = eventRepository.findAll();

        List<EventComCoordenadas> eventosComCoordenadas = eventos.stream()
                .filter(evento -> evento.getStatus() == StatusEnum.OPEN)
                .map(evento -> new EventComCoordenadas(evento, new CoordinatesDTO(evento.getLatitude(), evento.getLongitude())))
                .collect(Collectors.toList());

        return eventosProximos(coordenadaReferencia, eventosComCoordenadas);
    }

    private List<EventDTO> eventosProximos(CoordinatesDTO minhaLocalizacao, List<EventComCoordenadas> eventosComCoordenadas) {

        List<EventComDistancia> eventosComDistancia = new ArrayList<>();

        for (EventComCoordenadas eventoComCoordenadas : eventosComCoordenadas) {
            double distancia = calcularDistancia(minhaLocalizacao, eventoComCoordenadas.getCoordenadas());
            eventosComDistancia.add(new EventComDistancia(eventoComCoordenadas.getEvent(), distancia));
        }

        eventosComDistancia.sort(Comparator.comparingDouble(EventComDistancia::getDistancia));

        return eventosComDistancia.stream()
                .limit(3)
                .map(EventComDistancia::getEvent)
                .map(this::toEventDTO)
                .collect(Collectors.toList());
    }

    private double calcularDistancia(CoordinatesDTO dt1, CoordinatesDTO dt2) {
        final int RAIO_TERRA_KM = 6371;

        double dLat = Math.toRadians(dt2.getLatitude() - dt1.getLatitude());
        double dLon = Math.toRadians(dt2.getLongitude() - dt1.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(dt1.getLatitude())) * Math.cos(Math.toRadians(dt2.getLatitude())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RAIO_TERRA_KM * c;
    }

    private EventDTO toEventDTO(Event event) {
        return new EventDTO(event.getId(),
        event.getName(),
        event.getCity(),
        event.getDistrict(),
        event.getAddress(),
        event.getNumber(),
        event.getPhone(),
        event.getLatitude(),
        event.getLongitude(),
        event.getDescription(),
        event.getStatus().name(),
        event.getEnterprise().getId()
        );
    }

}
