package br.edu.ufersa.tracesuport.TraceSuport.domain.services;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventDTO;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService (EventRepository repo){
        this.eventRepository = repo;
    }

    public List<EventDTO> listar() {
        return eventRepository.findAll()
                .stream()
                .map(event -> new EventDTO(event))
                .collect(Collectors.toList()); // Desde o Java 16, você pode usar toList() diretamente
    }

}
