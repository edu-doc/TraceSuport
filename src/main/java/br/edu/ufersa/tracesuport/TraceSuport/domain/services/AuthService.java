package br.edu.ufersa.tracesuport.TraceSuport.domain.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.LoginRequest;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.RefreshTokenRequest;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.LoginResponse;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.UserResponseDTO;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.User;
import br.edu.ufersa.tracesuport.TraceSuport.domain.exceptions.InvalidTokenException;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.UserRepository;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtTokenService jwtTokenService;

    public AuthService(UserRepository userRepository, JwtTokenService jwtTokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetails user = (UserDetails) authentication.getPrincipal();

        User userEntity = userRepository.findByEmail(user.getUsername())
                                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new LoginResponse(jwtTokenService.generateToken(user), jwtTokenService.generateRefreshToken(userEntity), new UserResponseDTO(userEntity));
    }

    public LoginResponse refreshToken(RefreshTokenRequest request) throws InvalidTokenException {
        DecodedJWT token = jwtTokenService.getDecodedJWT(request.getRefreshToken());

        if (token == null) {
            throw new InvalidTokenException("O token não é válido");
        }

        String email = token.getSubject();

        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

        return new LoginResponse(jwtTokenService.generateToken(user), request.getRefreshToken(), new UserResponseDTO(user));
    }
}
