package com.ExpressLane.Auth.Filters;

import com.ExpressLane.Configuration.AuthCredentials;
import com.ExpressLane.Auth.Util.TokenUtils;
import com.ExpressLane.Auth.Util.UserDetailsImpl;
import com.ExpressLane.Model.Role;
import com.ExpressLane.Model.RoleName;
import com.ExpressLane.Model.User;
import com.ExpressLane.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.AuthenticationException;


import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/api/auth/login");
    }

   @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {



       try {
           AuthCredentials authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
           // Crear un UsernamePasswordAuthenticationToken
           UsernamePasswordAuthenticationToken userNamePAT = new UsernamePasswordAuthenticationToken(
                   authCredentials.getEmail(),
                   authCredentials.getPassword()
           );
           return authenticationManager.authenticate(userNamePAT);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl)authResult.getPrincipal();

        // Obtener el usuario desde el repositorio si es necesario
        User user = userRepository.findOneByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

       // Obtener los roles del usuario
        List<RoleName> userRoles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        // Generar el token con el nombre, email y roles
        String token = TokenUtils.generateToken(user.getName(),
                user.getEmail(), userRoles);

        // Agregar el token al header de la respuesta
        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
