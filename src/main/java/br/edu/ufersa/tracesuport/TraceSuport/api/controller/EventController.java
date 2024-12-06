package br.edu.ufersa.tracesuport.TraceSuport.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.ufersa.tracesuport.TraceSuport.domain.services.EventService;
import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventDTO;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.Event;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.EventRepository;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService service) {
        this.eventService = service;
    }

    @GetMapping
    public ResponseEntity<?> getEvents() {
        ResponseEntity<List<EventDTO>> response = new ResponseEntity<List<EventDTO>>(eventService.listar(), HttpStatus.OK);
        return response;
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
        ResponseEntity<EventDTO> response = new ResponseEntity<EventDTO>(eventService.criar(eventDTO), HttpStatus.OK);
        return response;
    }

    

    

}
