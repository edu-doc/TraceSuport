package br.edu.ufersa.tracesuport.TraceSuport.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventDTO;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.EventRepository;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    @Autowired
    EventRepository repository; 

    @GetMapping
    public List<EventDTO> listar() {
        return repository.findAll()
                         .stream()
                         .map(event -> new EventDTO(event.getId(), event.getName()))
                         .toList(); // Desde o Java 16, você pode usar toList() diretamente
    }
    

    

}
