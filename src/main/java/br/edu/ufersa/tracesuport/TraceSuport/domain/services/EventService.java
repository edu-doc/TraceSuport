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

    public CoordinatesDTO maisProximo (Long id) throws IllegalArgumentException{

        Event eventoReferencia = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("chamado não encontrado com esse ID"));

        CoordinatesDTO coordenadaReferencia = new CoordinatesDTO(eventoReferencia.getLatitude(), eventoReferencia.getLongitude());

        List<CoordinatesDTO> coordenadas = eventRepository.findByEnterprise(eventoReferencia.getEnterprise())
                .stream()
                .filter(evento -> !evento.getId().equals(id)) // Excluir o evento de referência da busca
                .map(evento -> new CoordinatesDTO(evento.getLatitude(), evento.getLongitude()))
                .collect(Collectors.toList());

        return coordenadasProxima(coordenadaReferencia, coordenadas);
    }

    private CoordinatesDTO coordenadasProxima(CoordinatesDTO minhaLocalizacao, List<CoordinatesDTO> coordenadas){

        CoordinatesDTO maisProxima = null;
        double menorDistancia = Double.MAX_VALUE;

        for (CoordinatesDTO coordenada : coordenadas) {
            double distancia = calcularDistancia(
                    minhaLocalizacao,
                    coordenada
            );

            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                maisProxima = coordenada;
            }
        }

        return maisProxima;

    }

    private double calcularDistancia(CoordinatesDTO dt1, CoordinatesDTO dt2) {

        final int RAIO_TERRA_KM = 6371; // Raio médio da Terra em km

        double dLat = Math.toRadians(dt2.getLatitude() - dt1.getLatitude());
        double dLon = Math.toRadians(dt2.getLongitude() - dt1.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(dt1.getLatitude())) * Math.cos(Math.toRadians(dt2.getLatitude())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RAIO_TERRA_KM * c;
    }

}
