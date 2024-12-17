package br.edu.ufersa.tracesuport.TraceSuport.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.EnterpriseRequest;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.LoginRequest;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.RefreshTokenRequest;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.UserRequestDTO;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.EnterpriseResponse;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.LoginResponse;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.UserResponseDTO;
import br.edu.ufersa.tracesuport.TraceSuport.api.exceptions.InvalidTokenException;
import br.edu.ufersa.tracesuport.TraceSuport.domain.services.AuthService;
import br.edu.ufersa.tracesuport.TraceSuport.domain.services.EnterpriseService;
import br.edu.ufersa.tracesuport.TraceSuport.domain.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    private final UserService userService;

    private final EnterpriseService enterpriseService;

    private final HttpServletRequest httpServletRequest;

    public AuthController(AuthService authService, 
                          HttpServletRequest httpServletRequest,
                          UserService userService,
                          EnterpriseService enterpriseService) {
        this.authService = authService;
        this.httpServletRequest = httpServletRequest;
        this.userService = userService;
        this.enterpriseService = enterpriseService;
    }

    @PostMapping("enterprise/register")
    public ResponseEntity<?> register(@Valid @RequestBody EnterpriseRequest request) {
        return new ResponseEntity<EnterpriseResponse>(enterpriseService.create(request), HttpStatus.CREATED);
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO request) {
        return new ResponseEntity<UserResponseDTO>(userService.create(request), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return new ResponseEntity<LoginResponse>(authService.login(request, httpServletRequest.getHeader("User-Agent")), HttpStatus.OK);
    }

    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) throws InvalidTokenException {
        return new ResponseEntity<LoginResponse>(authService.refreshToken(request, httpServletRequest.getHeader("User-Agent")), HttpStatus.OK);
    }
}
