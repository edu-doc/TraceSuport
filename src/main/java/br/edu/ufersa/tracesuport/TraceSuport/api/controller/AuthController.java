package br.edu.ufersa.tracesuport.TraceSuport.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.LoginRequest;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.RefreshTokenRequest;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.LoginResponse;
import br.edu.ufersa.tracesuport.TraceSuport.domain.exceptions.InvalidTokenException;
import br.edu.ufersa.tracesuport.TraceSuport.domain.services.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return new ResponseEntity<LoginResponse>(authService.login(request), HttpStatus.OK);
    }

    @PostMapping("refreshToken")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) throws InvalidTokenException {
        return new ResponseEntity<LoginResponse>(authService.refreshToken(request), HttpStatus.OK);
    }
}
