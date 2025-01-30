package br.edu.ufersa.tracesuport.TraceSuport.domain.filters;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import br.edu.ufersa.tracesuport.TraceSuport.domain.configuration.SecurityConfiguration;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.User;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.UserRepository;
import br.edu.ufersa.tracesuport.TraceSuport.domain.services.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (checkIfEndpointIsNotPublic(request)) {
                String token = recoveryToken(request);
                if (token != null) {
                    String subject = jwtTokenService.getSubjectFromToken(token);

                    String type = jwtTokenService.getDecodedJWT(token).getClaim("type").asString();

                    String userAgent = jwtTokenService.getDecodedJWT(token).getClaim("user-agent").asString();

                    if (!userAgent.equals(request.getHeader("User-Agent"))) {
                        throw new RuntimeException("Token inválido");
                    }

                    if (!type.equals("access-token")) {
                        throw new RuntimeException("O token não é válido para esse recurso");
                    }

                    User user = userRepository.findByEmail(subject).get();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new RuntimeException("O token está ausente.");
                }
            }
        } catch (JWTVerificationException e) {
            response.setStatus(401);

            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token inválido\", \"status\": 401}");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        boolean isUploads = requestURI.startsWith("/uploads");

        boolean isPubic = Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);

        return !isPubic && !isUploads;
    }

}
