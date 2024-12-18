package br.edu.ufersa.tracesuport.TraceSuport.domain.services;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.CoordinatesDTO;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventDTO;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Enterprise;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.User;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.EnterpriseRepository;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.EventRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

        return new EventDTO(eventRepository.save(event));
    }

    public EventDTO atualizar(EventDTO dto) throws IllegalArgumentException {
        Optional<Event> existingEvent = eventRepository.findById(dto.getId());

        if (existingEvent.isEmpty()) {
            throw new IllegalArgumentException("chamado não encontrado com esse ID");
        }

        return new EventDTO(eventRepository.save(new Event(dto)));
    }

    public CoordinatesDTO obterCoordenadas(Long id){
        return eventRepository.findById(id)
                .map(event -> new CoordinatesDTO(event.getLatitude(), event.getLongitude()))
                .orElseThrow(() -> new IllegalArgumentException("não possui chamado com esse ID"));

    }


    public EventDTO deletar(Long id) throws DataIntegrityViolationException {
        return eventRepository.findById(id)
                .map(event -> {
                    EventDTO deleteDto = new EventDTO(event);
                    eventRepository.delete(event);
                    return deleteDto;
                })
                .orElseThrow(() -> new IllegalArgumentException("chamado não encontrado"));
    }

}
