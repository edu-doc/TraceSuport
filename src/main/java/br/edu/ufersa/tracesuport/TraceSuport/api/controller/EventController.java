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

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Long id) {
        return new ResponseEntity<EventDTO>(eventService.get(id), HttpStatus.OK);
    }

    @GetMapping("/coordinatesProximas/{id}")
    public ResponseEntity<?> getCoordinatesProximas(@PathVariable Double latitude, @PathVariable Double longitude) {
        return new ResponseEntity<CoordinatesDTO>((CoordinatesDTO) eventService.maisProximo(latitude, longitude), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        return new ResponseEntity<EventDTO>(eventService.criar(eventDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody EventDTO eventDTO, @PathVariable Long id) {
        return new ResponseEntity<EventDTO>(eventService.atualizar(eventDTO, id), HttpStatus.OK);
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
