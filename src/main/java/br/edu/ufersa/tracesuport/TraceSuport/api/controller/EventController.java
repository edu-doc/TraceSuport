package br.edu.ufersa.tracesuport.TraceSuport.api.controller;

import java.util.List;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.CoordinatesDTO;
import br.edu.ufersa.tracesuport.TraceSuport.domain.services.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.EventDTO;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService service) {
        this.eventService = service;
    }

    @GetMapping
    public ResponseEntity<?> getEvents() {
        return new ResponseEntity<List<EventDTO>>(eventService.listar(), HttpStatus.OK);
    }

    @GetMapping("/coordinatesProximas/{id}")
    public ResponseEntity<?> getCoordinatesProximas(@PathVariable Long id) {
        return new ResponseEntity<CoordinatesDTO>(eventService.maisProximo(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        return new ResponseEntity<EventDTO>(eventService.criar(eventDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateEvent(@Valid @RequestBody EventDTO eventDTO) {
        return new ResponseEntity<EventDTO>(eventService.atualizar(eventDTO), HttpStatus.OK);
    }    

    @GetMapping("/coordinates/{id}")
    public ResponseEntity<?> getCoordinates(@PathVariable Long id) {
        return new ResponseEntity<CoordinatesDTO>(eventService.obterCoordenadas(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        return new ResponseEntity<EventDTO>(eventService.deletar(id), HttpStatus.OK);
    }

}
