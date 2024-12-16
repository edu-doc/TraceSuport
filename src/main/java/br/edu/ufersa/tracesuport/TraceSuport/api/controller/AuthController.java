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
import br.edu.ufersa.tracesuport.TraceSuport.api.exceptions.InvalidTokenException;
import br.edu.ufersa.tracesuport.TraceSuport.domain.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    private final HttpServletRequest httpServletRequest;

    public AuthController(AuthService authService, HttpServletRequest httpServletRequest) {
        this.authService = authService;
        this.httpServletRequest = httpServletRequest;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return new ResponseEntity<LoginResponse>(authService.login(request, httpServletRequest.getHeader("User-Agent")), HttpStatus.OK);
    }

    @PostMapping("refreshToken")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) throws InvalidTokenException {
        return new ResponseEntity<LoginResponse>(authService.refreshToken(request, httpServletRequest.getHeader("User-Agent")), HttpStatus.OK);
    }
}
