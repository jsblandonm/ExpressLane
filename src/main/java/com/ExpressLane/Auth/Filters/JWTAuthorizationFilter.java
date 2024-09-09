package com.ExpressLane.Auth.Filters;

import com.ExpressLane.Auth.Util.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");

        // Si el token no es nulo y comienza con Bearer, extrae el token
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.replace("Bearer ", "");
            //Extrae roles autenticacion de token
            UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAuthentication(token);

            if (usernamePAT != null) {
                // Setear la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(usernamePAT);

            }
        }
        //Continua con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);

    }
}
