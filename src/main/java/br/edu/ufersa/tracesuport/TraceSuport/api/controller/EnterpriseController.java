package br.edu.ufersa.tracesuport.TraceSuport.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.EnterpriseRequest;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.EnterpriseResponse;
import br.edu.ufersa.tracesuport.TraceSuport.domain.services.EnterpriseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/enterprise")
public class EnterpriseController {
    private final EnterpriseService enterpriseService;

    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody EnterpriseRequest request) {
        return new ResponseEntity<EnterpriseResponse>(enterpriseService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public EnterpriseResponse find(@PathVariable Long id) {
        return enterpriseService.find(id);
    }

    @GetMapping
    public List<EnterpriseResponse> findAll() {
        return enterpriseService.findAll();
    } 
}
