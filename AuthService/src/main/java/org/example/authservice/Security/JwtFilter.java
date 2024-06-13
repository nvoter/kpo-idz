package org.example.authservice.Security;

import org.example.authservice.Exceptions.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtSource jwtSource;

    @Autowired
    public JwtFilter(JwtSource jwtSource) {
        this.jwtSource = jwtSource;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtSource.resolveAccessToken(request);

        try {
            if (token != null && jwtSource.validateToken(token)) {
                Authentication auth = jwtSource.getAuth(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (AuthException e) {
            String errorMessage = "Incorrect or expired token";
            e = new AuthException(errorMessage, HttpStatus.UNAUTHORIZED);

            SecurityContextHolder.clearContext();

            response.sendError(e.getHttpStatus().value(), errorMessage);

            throw e;
        }

        chain.doFilter(request, response);
    }
}