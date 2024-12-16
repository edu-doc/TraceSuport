package br.edu.ufersa.tracesuport.TraceSuport.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.UserRequestDTO;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.UserResponseDTO;
import br.edu.ufersa.tracesuport.TraceSuport.domain.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request) {
        return new ResponseEntity<UserResponseDTO>(userService.create(request), HttpStatus.CREATED);
    }
}
